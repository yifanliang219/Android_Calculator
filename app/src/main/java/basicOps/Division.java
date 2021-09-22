package basicOps;

import field.*;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class Division {

    public static Field fields(Field a, Field b) {

        Field result = null;

        if (a instanceof Complex && b instanceof Complex) {
            result = complex((Complex) a, (Complex) b);
        } else if (a instanceof Real && b instanceof Real) {
            result = real((Real) a, (Real) b);
        } else if (a instanceof Complex && b instanceof Real) {
            result = complex((Complex) a, (Real) b);
        } else if (a instanceof Real && b instanceof Complex) {
            result = complex((Real) a, (Complex) b);
        }

        return result;

    }


    private static Complex complex(Complex a, Complex b) {
        if (b.isZero()) {
            throw new ArithmeticException("Error: Divide by zero.");
        } else {

            Real resultR = Division.real(Addition.real(Multiplication.real(a.re, b.re), Multiplication.real(a.im, b.im)), Addition.real(Multiplication.real(b.re, b.re), Multiplication.real(b.im, b.im)));
            Real resultI = Division.real(Subtraction.real(Multiplication.real(a.im, b.re), Multiplication.real(a.re, b.im)), Addition.real(Multiplication.real(b.re, b.re), Multiplication.real(b.im, b.im)));
            return new Complex(resultR, resultI);
        }
    }

    private static Complex complex(Complex a, Real b) {
        if (b.isZero()) {
            throw new ArithmeticException("Error: Divide by zero.");
        } else {

            return new Complex(Division.real(a.re, b), Division.real(a.im, b));
        }
    }

    private static Complex complex(Real a, Complex b) {
        if (b.isZero()) {
            throw new ArithmeticException("Error: Divide by zero.");
        } else {
            Real temp = Addition.real(Multiplication.real(b.re, b.re), Multiplication.real(b.im, b.im));
            Real multiplier = Division.real(a, temp);
            return Multiplication.complex(b.congruent(), multiplier);
        }
    }

    protected static Real real(Real r1, Real r2) {

        Real result;

        if (r2.isZero()) {
            throw new ArithmeticException("Error: Divide by zero.");
        } else {
            if (r1 instanceof IntFraction && r2 instanceof IntFraction) {
                IntFraction a = (IntFraction) r1;
                IntFraction b = (IntFraction) r2;
                ArrayList<BigInteger> ints = new ArrayList<>();
                ints.add(a.numerator.abs());
                ints.add(a.denominator.abs());
                ints.add(b.numerator.abs());
                ints.add(b.denominator.abs());
                if (Collections.max(ints).doubleValue() > (Math.pow(10, 5))) {
                    result = new Decimal(a.value().divide(b.value(), 64, RoundingMode.HALF_UP));
                } else
                    result = new IntFraction(a.numerator.multiply(b.denominator), a.denominator.multiply(b.numerator));
            } else if (r1 instanceof IntFraction && r2 instanceof Decimal) {
                if (((Decimal) r2).isSmall()) {
                    result = real(r1, ((Decimal) r2).convert_to_fraction());
                } else result = new Decimal(r1.value().divide(r2.value(), 64, RoundingMode.HALF_UP));
            } else if (r1 instanceof Decimal && r2 instanceof IntFraction) {
                if (((Decimal) r1).isSmall()) {
                    result = real(((Decimal) r1).convert_to_fraction(), r2);
                } else result = new Decimal(r1.value().divide(r2.value(), 64, RoundingMode.HALF_UP));
            } else result = new Decimal(r1.value().divide(r2.value(), 64, RoundingMode.HALF_UP));

        }

        if (new Decimal(result.value()).isSmall()) return new Decimal(result.value()).convert_to_fraction();

        return result;
    }

}
