package cs3524.mud;

import java.io.Serializable;

class Item implements Serializable {
    public String name;
    public double weight;

    public Item(String name, double weight) {
        if (name.equals("")) {
            return;
        }
        this.name        = name;
        this.weight      = weight;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Item)) {
            return false;
        }
        else if (obj == this) {
            return true;
        }
        else {
            Item i = (Item) obj;
            if (i.name.equals(this.name)) {
                return true;
            }
            else {
                return false;
            }
        }
    }
}