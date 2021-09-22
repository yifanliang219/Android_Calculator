package cal;

import field.Field;
import lexer.Operator;

public class UnaryOperatorCal implements Cal {

    private Operator unaryOp;
    private Field arg;

    public UnaryOperatorCal(Operator unaryOp, Field arg) {
        this.unaryOp = unaryOp;
        this.arg = arg;
    }

    @Override
    public Field cal() {
        switch (unaryOp) {
            case UNARY_PLUS:
                return arg;
            case UNARY_MINUS:
                return arg.negate();
            default:
                throw new ArithmeticException("Illegal arguments.");
        }
    }

}
