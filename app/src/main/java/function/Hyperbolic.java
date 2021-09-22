package function;

import field.Complex;
import field.Field;
import field.IntFraction;
import field.Real;

public class Hyperbolic {

    public static Field sinh(Field f) {
        Real x = f.real();
        Real y = f.imag();
        double sinhxcosy = Math.sinh(x.value().doubleValue()) * Math.cos(y.value().doubleValue());
        double coshxsiny = Math.cosh(x.value().doubleValue()) * Math.sin(y.value().doubleValue());
        return new Complex(sinhxcosy, coshxsiny);
    }

    public static Field cosh(Field f) {
        Real x = f.real();
        Real y = f.imag();
        double coshxcosy = Math.cosh(x.value().doubleValue()) * Math.cos(y.value().doubleValue());
        double sinhxsiny = Math.sinh(x.value().doubleValue()) * Math.sin(y.value().doubleValue());
        return new Complex(coshxcosy, sinhxsiny);
    }

    public static Field tanh(Field f) {
        try {
            return sinh(f).div(cosh(f));
        } catch (Exception e) {
            throw new ArithmeticException("Error(tanh): Illegal argument.");
        }
    }

    public static Field arcsinh(Field f) {
        if (!f.imag().isZero())
            throw new ArithmeticException("Error(sinh⁻¹): Illegal argument.");
        Real x = f.real();
        double insideIn = x.add(x.exp(new IntFraction(2)).add(new IntFraction(1)).real().sqrt()).real().value().doubleValue();
        return new Complex(Math.log(insideIn), 0);
    }

    public static Field arccosh(Field f) {
        if (!f.imag().isZero())
            throw new ArithmeticException("Error(cosh⁻¹): Illegal argument.");
        if (f.real().lessThan(new IntFraction(1)))
            throw new ArithmeticException("Error(cosh⁻¹): Illegal argument.");
        Real x = f.real();
        double insideIn = x.add(x.exp(new IntFraction(2)).sub(new Complex(1, 0)).real().sqrt()).real().value().doubleValue();
        return new Complex(Math.log(insideIn), 0);

    }

    public static Field arctanh(Field f) {
        if (!f.imag().isZero())
            throw new ArithmeticException("Error(tanh⁻¹): Illegal argument.");
        if (!f.modulus().lessThan(new IntFraction(1)))
            throw new ArithmeticException("Error(tanh⁻¹): Illegal argument.");
        Real insideIn = new IntFraction(1).add(f).div((new IntFraction(1).sub(f))).real();
        return new Complex(0.5 * Math.log(insideIn.value().doubleValue()), 0);
    }

}
