package lab.product;

import lab.consumer.Consumer;
import lab.producer.Producer;

import javax.swing.*;

public class Product extends JLabel {
    private Producer producer;
    private Consumer consumer;
    private static int count;
    private int id;

    private Product(Producer producer) {
        super(producer.getName() + ":" + count);
        this.producer = producer;
        // label
        this.setVisible(true);
        this.setBounds(10,30,100,100);
        this.id = count;
    }

    synchronized public static Product getOne(Producer producer) {
        count++;
        return new Product(producer);
    }

    public void consume(Consumer consumer) {
        this.consumer = consumer;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public void setX(int x) {
        this.setBounds(x,30,100,100);
    }

    public int getId() {
        return id;
    }
}
