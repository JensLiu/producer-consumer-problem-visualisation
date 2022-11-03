package lab.producer;

import lab.product.Product;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProducerPanel extends JPanel {
    JButton btn_start = new JButton("start");
    JButton btn_stop = new JButton("stop");
    ProducerThread producerThread;

//    JLabel ballLabel;

    private int PANEL_W = 850;
    private int PANEL_H = 500;
    private int label_x = 0; // x-axis of the ball
    private int label_y = PANEL_H / 2;
    private int LABEL_W = 10;
    private int LABEL_H = 10;
    private int waitms = 300;

    public ProducerPanel(Producer producer) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
                "Producer", TitledBorder.CENTER, TitledBorder.TOP));
        setSize(PANEL_W, PANEL_H);
//        setBackground(new Color(22, 210, 188));

        btn_start.setBounds(600, 25, 100, 50);
        btn_stop.setBounds(700, 25, 100, 50);
        producerThread = new ProducerThread(producer, this);

        this.add(btn_start);
        this.add(btn_stop);
        setVisible(true);
        producerThread.start();

        btn_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                producerThread.resumeProducer();
            }
        });
        btn_stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                producerThread.stopProducer();
            }
        });
    }

    public void targetAnimation(Product product) {
        this.add(product);
        for (int i = 0; producerThread.isRunning() && i < 10; i++) {
            label_x = i * 10;
//            product.setBounds(label_x, label_y, LABEL_W, LABEL_H );
            product.setX(label_x);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
            }
        }
        this.remove(product);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}