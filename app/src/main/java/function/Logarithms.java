package function;

import field.Complex;
import field.Field;
import field.IntFraction;

public class Logarithms {

    public static Field In(Field f) {
        if (!f.real().greaterThan(new IntFraction(0)) | !f.imag().isZero())
            throw new ArithmeticException("Error(In/log): Illegal argument.");
        double arg = f.real().value().doubleValue();
        return new Complex(Math.log(arg), 0);
    }

    public static Field log(Field b, Field x) {
        if (b.equals(new IntFraction(1))) throw new ArithmeticException("Error(In/log): Illegal argument.");
        return In(x).div(In(b)).real();
    }

}
