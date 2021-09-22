package basicOps;

import field.*;

import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class Addition {

    public static Field fields(Field a, Field b) {

        Field result = null;

        if (a instanceof Complex && b instanceof Complex) {
            result = complex((Complex) a, (Complex) b);
        } else if (a instanceof Real && b instanceof Real) {
            result = real((Real) a, (Real) b);
        } else if (a instanceof Complex && b instanceof Real) {
            result = complex((Complex) a, (Real) b);
        } else if (a instanceof Real && b instanceof Complex) {
            result = complex((Complex) b, (Real) a);
        }

        return result;

    }

    private static Complex complex(Complex a, Complex b) {
        return new Complex(Addition.real(a.re, b.re), Addition.real(a.im, b.im));
    }

    private static Complex complex(Complex a, Real b) {
        return new Complex(Addition.real(a.re, b), a.im);
    }

    protected static Real real(Real r1, Real r2) {

        Real result;

        if (r1 instanceof IntFraction && r2 instanceof IntFraction) {
            IntFraction a = (IntFraction) r1;
            IntFraction b = (IntFraction) r2;
            ArrayList<BigInteger> ints = new ArrayList<>();
            ints.add(a.numerator.abs());
            ints.add(a.denominator.abs());
            ints.add(b.numerator.abs());
            ints.add(b.denominator.abs());
            if (Collections.max(ints).doubleValue() > (Math.pow(10, 5))) {
                result = new Decimal(a.value().add(b.value(), new MathContext(64, RoundingMode.HALF_UP)));
            } else
                result = new IntFraction(a.numerator.multiply(b.denominator).add(b.numerator.multiply(a.denominator)), a.denominator.multiply(b.denominator));
        } else if (r1 instanceof IntFraction && r2 instanceof Decimal) {
            if (((Decimal) r2).isSmall()) {
                result = real(r1, ((Decimal) r2).convert_to_fraction());
            } else result = new Decimal(r1.value().add(r2.value(), new MathContext(64, RoundingMode.HALF_UP)));
        } else if (r1 instanceof Decimal && r2 instanceof IntFraction) {
            result = real(r2, r1);
        } else result = new Decimal(r1.value().add(r2.value(), new MathContext(64, RoundingMode.HALF_UP)));

        if (new Decimal(result.value()).isSmall()) return new Decimal(result.value()).convert_to_fraction();

        return result;
    }

}
