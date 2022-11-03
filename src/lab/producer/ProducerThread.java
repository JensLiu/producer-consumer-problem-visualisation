package lab.producer;
import lab.product.Product;

public class ProducerThread extends Thread {
    private Producer producer;
    private boolean running;
    private ProducerPanel panel;

    ProducerThread(Producer p, ProducerPanel panel) {
        producer = p;
        running = true;
        this.panel = panel;
    }

    @Override
    public void run() {
        while (true) {
            if (running) {
                Product p = Product.getOne(producer);
                // play animation
                panel.targetAnimation(p);
                // produce
                producer.buf.produceOne(p);
            } else {
                try {
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.printf(producer.getName() + " interrupted\n");
                }
            }
        }
    }

    public void stopProducer() {
        running = false;
    }

    public void resumeProducer() {
        running = true;
        this.interrupt();
    }

    public boolean isRunning() {
        return running;
    }

}