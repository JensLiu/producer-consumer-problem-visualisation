package lab.consumer;

import lab.product.Product;

public class ConsumerThread extends Thread {

    private Consumer consumer;

    private boolean running;
    private int waitms = 300;

    private ConsumerPanel panel;
    ConsumerThread(Consumer c, ConsumerPanel panel) {
        consumer = c;
        running = true;
        this.panel = panel;
    }

    @Override
    public void run() {
        while (true) {
            if (running) {
                Product p = consumer.consumeOne();
                panel.targetAnimation(p);
                try {
                    Thread.sleep(waitms);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.printf(consumer.getName() + " interrupted\n");
                }
            }
        }
    }
    public void stopConsumer() {
        running = false;
    }

    public void resumeConsumer() {
        running = true;
        this.interrupt();
    }

    public boolean isRunning() {
        return running;
    }


}
