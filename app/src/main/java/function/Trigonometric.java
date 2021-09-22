package function;

import field.Complex;
import field.Field;
import field.IntFraction;
import field.Real;

public class Trigonometric {

    public static Field sin(Field f) {
        Real x = f.real();
        Real y = f.imag();
        double sinxcoshy = Math.sin(x.value().doubleValue()) * Math.cosh(y.value().doubleValue());
        double cosxsinhy = Math.cos(x.value().doubleValue()) * Math.sinh(y.value().doubleValue());
        return new Complex(sinxcoshy, cosxsinhy);
    }

    public static Field cos(Field f) {
        Real x = f.real();
        Real y = f.imag();
        double cosxcoshy = Math.cos(x.value().doubleValue()) * Math.cosh(y.value().doubleValue());
        double sinxsinhy = Math.sin(x.value().doubleValue()) * Math.sinh(y.value().doubleValue());
        return new Complex(cosxcoshy, -sinxsinhy);
    }

    public static Field tan(Field f) {
        try {
            return sin(f).div(cos(f));
        } catch (Exception e) {
            throw new ArithmeticException("Error(tan): Illegal argument.");
        }
    }

    public static Field arcsin(Field f) {
        if (!f.imag().isZero()) throw new ArithmeticException("Error(sin⁻¹): Illegal argument.");
        if (f.modulus().greaterThan(new IntFraction(1)))
            throw new ArithmeticException("Error(sin⁻¹): Illegal argument.");
        double arg = f.real().value().doubleValue();
        return new Complex(Math.asin(arg), 0);
    }

    public static Field arccos(Field f) {
        if (!f.imag().isZero()) throw new ArithmeticException("Error(cos⁻¹): Illegal argument.");
        if (f.modulus().greaterThan(new IntFraction(1)))
            throw new ArithmeticException("Error(cos⁻¹): Illegal argument.");
        double arg = f.real().value().doubleValue();
        return new Complex(Math.acos(arg), 0);
    }

    public static Field arctan(Field f) {
        if (!f.imag().isZero()) throw new ArithmeticException("Error(tan⁻¹): Illegal argument.");
        double arg = f.real().value().doubleValue();
        return new Complex(Math.atan(arg), 0);
    }

}
