package lab.consumer;

import lab.product.Product;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsumerPanel extends JPanel {
    JButton btn_start = new JButton("start");
    JButton btn_stop = new JButton("stop");
    ConsumerThread consumerThread;

    int PANEL_W = 850;
    int PANEL_H = 500;
    int label_x = 0; // x-axis of the ball
    int label_y = PANEL_H / 2;
    int LABEL_W = 10;
    int LABEL_H = 10;
    int waitms = 300;

    public ConsumerPanel(Consumer consumer) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
                "consumer", TitledBorder.CENTER, TitledBorder.TOP));
        setSize(850, 500);

        btn_start.setBounds(600, 25, 100, 50);
        btn_stop.setBounds(700, 25, 100, 50);
        consumerThread = new ConsumerThread(consumer, this);

        this.add(btn_start);
        this.add(btn_stop);
        setVisible(true);
        consumerThread.start();

        btn_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consumerThread.resumeConsumer();
            }
        });
        btn_stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consumerThread.stopConsumer();
            }
        });
    }

    void targetAnimation(Product product) {
        this.add(product);
        for (int i = 10; consumerThread.isRunning() && i >= 0; i--) {
            label_x = i * 10;
            product.setX(label_x);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        this.remove(product);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

}
