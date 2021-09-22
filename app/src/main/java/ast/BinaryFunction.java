package ast;

import cal.Visitor;
import field.Field;
import lexer.Function;

public class BinaryFunction implements Node {

    public final Function function;
    public Node leftChild;
    public Node rightChild;
    private int capacity = 2;

    public BinaryFunction(Function function) {
        this.function = function;
    }


    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public void attachChild(Node child) {
        switch (capacity) {
            case 2:
                rightChild = child;
                capacity--;
                break;
            case 1:
                leftChild = child;
                capacity--;
                break;
            case 0:
                System.err.println("Exceed node capacity: BinaryFunction(" + function.name() + ")");
                System.exit(-1);
        }
    }

    @Override
    public Field accept(Visitor v) {
        return v.visit(this);
    }


    @Override
    public String toString() {
        return function.name() + "[" + leftChild.toString() + "," + rightChild.toString() + "]";
    }

}
