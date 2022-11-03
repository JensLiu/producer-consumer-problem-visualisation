package lab.consumer;

import lab.MyRole;
import lab.product.Product;
import lab.storage.Storage;

public class Consumer extends MyRole {

    private Storage buf;

    public Consumer(String name, Storage s) {
        super(name);
        buf = s;
    }

    public Product consumeOne() {
        return buf.consumeOne(this);
    }

}