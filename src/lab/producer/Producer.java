package lab.producer;

import lab.product.Product;
import lab.MyRole;
import lab.storage.Storage;

public class Producer extends MyRole {
    Storage buf;
    public Producer(String name, Storage s) {
        super(name);
        buf = s;
    }

    Producer() {
        super("1");
    }
    public void produceOne() {
        Product p = Product.getOne(this);

        buf.produceOne(p);
    }

}


