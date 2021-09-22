package lexer;

import ast.Leaf;
import ast.Node;
import field.Complex;
import field.Decimal;
import field.IntFraction;

public enum Constant implements TokenClass {

    NUMBER, DIGIT, ANS, E, I, PI;


    @Override
    public boolean isFunction() {
        return false;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public boolean isOperator() {
        return false;
    }


    @Override
    public Node toNode(String data) {
        if (this == Constant.E) {
            return new Leaf(new Decimal(Math.E), data);
        } else if (this == Constant.I) {
            return new Leaf(new Complex(0, 1), data);
        } else if (this == Constant.PI) {
            return new Leaf(new Decimal(Math.PI), data);
        } else if (data.matches("\\d+\\.\\d+")) {
            Decimal f = new Decimal(data);
            if (f.isSmall()) {
                IntFraction fra = f.convert_to_fraction();
                return new Leaf(fra, fra.toString());
            } else return new Leaf(new Decimal(data), data);
        } else if (data.matches("\\d+")) {
            return new Leaf(new IntFraction(Integer.parseInt(data)), data);
        } else throw new ArithmeticException("Error: Illegal number format.");
    }

}
