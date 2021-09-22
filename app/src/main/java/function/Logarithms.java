package function;

import android.util.Log;

import java.math.BigInteger;

import field.Complex;
import field.Decimal;
import field.Field;
import field.IntFraction;
import field.Real;

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

    public static Field exp(Field b, Field p){
        try {
            if (p.isZero()) return new IntFraction(1);
            if (b.isZero()) return new IntFraction(0);
            else if (p.imag().isZero() && p.real().isInteger()) {
                if (p.real().lessThan(new IntFraction(0)))
                    return new IntFraction(1).div(exp(b, p.real().negate()));
                else return b.mul(exp(b, p.sub(new IntFraction(1))));
            } else if (b.imag().isZero() && b.real().lessThan(new IntFraction(0)) && p.imag().isZero() && p.real() instanceof IntFraction) {
                IntFraction n = (IntFraction) p.real();
                BigInteger numerator = n.numerator;
                BigInteger denominator = n.denominator;
                double r = b.modulus().value().doubleValue();
                double base = Math.pow(r, BigInteger.ONE.doubleValue() / denominator.doubleValue());
                if (denominator.intValue() % 2 != 0) {
                    return new Complex(-base, 0).exp(new IntFraction(numerator));
                } else {
                    return new Complex(0, base).exp(new IntFraction(numerator));
                }
            } else if (b.imag().isZero() && b.real().greaterThan(new IntFraction(0))) {
                Field insideTrig = p.imag().mul(Logarithms.In(b));
                double length = Math.pow(b.real().value().doubleValue(), p.real().value().doubleValue());
                Field cos = Trigonometric.cos(insideTrig);
                Field sin = Trigonometric.sin(insideTrig);
                Real re = new Decimal(length).mul(cos).real();
                Real im = new Decimal(length).mul(sin).real();
                return new Complex(re, im);
            } else throw new ArithmeticException("Error(exp): Illegal argument.");
        } catch (Exception e) {
            throw new ArithmeticException("Error(exp): Illegal argument.");
        }
    }

}
