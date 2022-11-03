package lab.storage;

import lab.product.Product;
import lab.consumer.Consumer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Storage extends JPanel {
    private final int MAX_STORAGE = 10;
    private Queue<Product> buf;
    private final ReentrantLock lock;
    private final Condition cdtn_waitOnFull;
    private final Condition cdtn_waitOnEmpty;

    public Storage() {
        // initialise
        buf = new LinkedList<>();
        lock = new ReentrantLock();
        cdtn_waitOnFull = lock.newCondition();
        cdtn_waitOnEmpty = lock.newCondition();

        // panel setup
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,2),
                "com.Storage", TitledBorder.CENTER, TitledBorder.TOP));
        setBackground(new Color(199, 190, 24));
        setVisible(true);

    }


    public Product consumeOne(Consumer consumer) {

        lock.lock();
        // begin critical sec.

        while (buf.size() == 0) {
            System.out.printf("***" + consumer.getName() + " wait "+ buf.size() + "\n");

            try {
                cdtn_waitOnEmpty.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Product p = buf.poll();
        p.setConsumer(consumer);

        // remove product (JLabel) from storage (JPanel)
        this.remove(p);
        this.repaint();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("---" + consumer.getName() + " consumed " + p.getId() + ", storage size "+ buf.size() + ", removed from " + p.getX() + "\n");

        cdtn_waitOnFull.signalAll();

        // end critical sec.
        lock.unlock();
        return p;
    }

    public void produceOne(Product product) {
        lock.lock();

        // begin critical sec.
        while (buf.size() == MAX_STORAGE) {
            System.out.printf("===" + product.getProducer().getName() + " wait, storage size "+ buf.size() + "\n");

            try {
                cdtn_waitOnFull.await();
            } catch (InterruptedException e) {
                System.out.printf("Interrupted on waiting");
            }
        }

        // add product (JLabel) to storage (JPanel)
        this.add(product);
        int position = this.getX() + 50 * (product.getId() % MAX_STORAGE);
        product.setX(position);
        this.repaint();

        buf.add(product);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("+++" + product.getProducer().getName() + " produced " + product.getId() + ", storage size " + buf.size() + ", placed at " + position + "\n");

        cdtn_waitOnEmpty.signalAll();
        // end critical sec

        lock.unlock();
    }

}