package ast;

import cal.Visitor;
import field.Field;

public class Leaf implements Node {

    public final Field value;
    private final String data;

    public Leaf(Field value, String data) {
        this.value = value;
        this.data = data;
    }

    @Override
    public int capacity() {
        return 0;
    }

    @Override
    public void attachChild(Node child) {
        System.err.println("Exceed node capacity: Leaf(" + value + ")");
        System.exit(-1);
    }


    @Override
    public String toString() {
        return "Leaf(" + data + ")";
    }

    @Override
    public Field accept(Visitor v) {
        return v.visit(this);
    }

}
