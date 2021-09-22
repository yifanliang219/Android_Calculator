package ast;

import cal.Visitor;
import field.Field;
import lexer.Function;

public class UnaryFunction implements Node {

    public final Function function;
    public Node child;
    private int capacity = 1;

    public UnaryFunction(Function function) {
        this.function = function;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public void attachChild(Node child) {
        switch (capacity) {
            case 1:
                this.child = child;
                capacity--;
                break;
            case 0:
                System.err.println("Exceed node capacity: UnaryFunction(" + function.name() + ")");
                System.exit(-1);
        }
    }


    @Override
    public String toString() {
        return function.name() + "[" + child.toString() + "]";
    }

    @Override
    public Field accept(Visitor v) {
        return v.visit(this);
    }

}
