package cal;

import field.Field;
import lexer.Operator;

public class BinaryOperatorCal implements Cal {

    private Operator binaryOp;
    private Field left;
    private Field right;

    public BinaryOperatorCal(Operator binaryOp, Field left, Field right) {
        this.binaryOp = binaryOp;
        this.left = left;
        this.right = right;
    }


    @Override
    public Field cal() {
        switch (binaryOp) {
            case ADD:
                return left.add(right);
            case SUB:
                return left.sub(right);
            case MUL:
            case PRIOR_MUL:
                return left.mul(right);
            case DIV:
                return left.div(right);
            case EXP:
                return left.exp(right);
            default:
                throw new ArithmeticException("Illegal arguments.");
        }
    }

}
