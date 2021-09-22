package cal;

import field.Field;
import function.Combinatorics;
import function.Logarithms;
import lexer.Function;

public class BinaryFunctionCal implements Cal {

    private Function binaryFunction;
    private Field left;
    private Field right;

    public BinaryFunctionCal(Function binaryFunction, Field left, Field right) {
        this.binaryFunction = binaryFunction;
        this.left = left;
        this.right = right;
    }

    @Override
    public Field cal() {
        switch (binaryFunction) {
            case NCR:
                return Combinatorics.nCr(left, right);
            case LOG:
                return Logarithms.log(left, right);
            default:
                throw new ArithmeticException("Illegal arguments.");
        }
    }

}
