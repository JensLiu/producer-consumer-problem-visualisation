import lab.consumer.Consumer;
import lab.consumer.ConsumerPanel;
import lab.producer.Producer;
import lab.producer.ProducerPanel;
import lab.storage.Storage;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    Storage storage = new Storage();
    Producer p1 = new Producer("P1", storage);
    Producer p2 = new Producer("P2", storage);
    Consumer c1 = new Consumer("C1", storage);
    Consumer c2 = new Consumer("C2", storage);

    ProducerPanel pp1 = new ProducerPanel(p1);
    ProducerPanel pp2 = new ProducerPanel(p2);
    ConsumerPanel cp1 = new ConsumerPanel(c1);
    ConsumerPanel cp2 = new ConsumerPanel(c2);
    public MainFrame() {
        this.setSize(850, 500);
        this.setLocationRelativeTo(null);
        Container container = this.getContentPane();

        GridLayout gridLayout = new GridLayout(5, 1);
        container.setLayout(gridLayout);

        container.add(pp1);
        container.add(pp2);
        container.add(storage);
        container.add(cp1);
        container.add(cp2);

        this.setVisible(true);
    }



}
