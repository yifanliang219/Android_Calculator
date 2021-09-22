package ast;

import cal.Visitor;
import field.Field;
import lexer.Operator;

public class UnaryOperator implements Node {

    public final Operator operator;
    public Node child;
    private int capacity = 1;

    public UnaryOperator(Operator operator) {
        this.operator = operator;
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
                System.err.println("Exceed node capacity: UnaryOperator(" + operator.name() + ")");
                System.exit(-1);
        }
    }


    public String toString() {
        return operator.name() + "[" + child.toString() + "]";
    }

    @Override
    public Field accept(Visitor v) {
        return v.visit(this);
    }

}
