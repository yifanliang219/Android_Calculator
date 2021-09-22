package ast;

import cal.Visitor;
import field.Field;
import lexer.Operator;

public class BinaryOperator implements Node {

    public final Operator operator;
    public Node leftChild;
    public Node rightChild;
    private int capacity = 2;

    public BinaryOperator(Operator operator) {
        this.operator = operator;
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
                System.err.println("Exceed node capacity: BinaryOperator(" + operator.name() + ")");
                System.exit(-1);
        }
    }


    @Override
    public String toString() {
        return operator.name() + "[" + leftChild.toString() + "," + rightChild.toString() + "]";
    }

    @Override
    public Field accept(Visitor v) {
        return v.visit(this);
    }

}
