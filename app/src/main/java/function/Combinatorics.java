package function;

import field.Complex;
import field.Field;

public class Combinatorics {

    public static Field factorial(Field f) {
        if (!f.imag().isZero() | !f.real().isInteger()) throw new ArithmeticException("Error(n!): Illegal argument.");
        if (f.isZero()) return new Complex(1, 0);
        else return f.mul(factorial(f.sub(new Complex(1, 0))));
    }

    public static Field nCr(Field n, Field r) {
        if(n.compareTo(r) < 0) throw new ArithmeticException("Error(n!): Illegal argument.");
        return factorial(n).div((factorial(r).mul(factorial(n.sub(r)))));
    }

}
