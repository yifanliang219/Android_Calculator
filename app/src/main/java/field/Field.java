package field;

import basicOps.Addition;
import basicOps.Division;
import basicOps.Multiplication;
import basicOps.Subtraction;
import function.Logarithms;
import function.Trigonometric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public abstract class Field implements Comparable<Field> {

    public abstract Decimal modulus();

    public abstract Real real();

    public abstract Real imag();

    public abstract Field congruent();

    public abstract Field negate();

    public abstract Field round_to_n_dp(int n);

    public abstract boolean isZero();


    public boolean equals(Field operand) {
        return this.compareTo(operand) == 0;
    }


    @Override
    public int compareTo(Field f) {

        BigDecimal left;
        BigDecimal right;
        if (this.imag().isZero()) {
            left = this.real().value();
        } else {
            left = this.modulus().value();
        }
        if (f.imag().isZero()) {
            right = f.real().value();
        } else {
            right = f.modulus().value();
        }
        return left.setScale(9, RoundingMode.HALF_UP).compareTo(right.setScale(9, RoundingMode.HALF_UP));

    }

    int compareTo(Number n) {
        return compareTo(new Decimal(n.doubleValue()));
    }


    public Field add(Field f) {
        return Addition.fields(this, f);
    }

    public Field sub(Field f) {
        return Subtraction.fields(this, f);
    }

    public Field mul(Field f) {
        return Multiplication.fields(this, f);
    }

    public Field div(Field f) {
        return Division.fields(this, f);
    }


    public Field exp(Field p) {
        try {
            if (p.isZero()) return new IntFraction(1);
            if (this.isZero()) return new IntFraction(0);
            else if (p.imag().isZero() && p.real().isInteger()) {
                if (p.real().lessThan(new IntFraction(0)))
                    return new IntFraction(1).div(exp(p.real().negate()));
                else return this.mul(exp(p.sub(new IntFraction(1))));
            } else if (this.imag().isZero() && this.real().lessThan(new IntFraction(0)) && p.imag().isZero() && p.real() instanceof IntFraction) {
                IntFraction n = (IntFraction) p.real();
                BigInteger numerator = n.numerator;
                BigInteger denominator = n.denominator;
                double r = this.modulus().value().doubleValue();
                double base = Math.pow(r, BigInteger.ONE.doubleValue() / denominator.doubleValue());
                if (denominator.intValue() % 2 != 0) {
                    return new Complex(-base, 0).exp(new IntFraction(numerator));
                } else {
                    return new Complex(0, base).exp(new IntFraction(numerator));
                }
            } else if (this.imag().isZero() && this.real().greaterThan(new IntFraction(0))) {
                Field insideTrig = p.imag().mul(Logarithms.In(this));
                double length = Math.pow(this.real().value().doubleValue(), p.real().value().doubleValue());
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
