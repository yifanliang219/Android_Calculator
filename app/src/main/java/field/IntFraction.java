package field;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public final class IntFraction extends Real {

    public final BigInteger numerator;
    public final BigInteger denominator;
    private BigDecimal value;


    public IntFraction(int value) {
        this(value, 1);
    }

    public IntFraction(BigInteger value) {
        this(value, BigInteger.ONE);
    }

    public IntFraction(int numerator, int denominator) {
        this(new BigInteger(String.valueOf(numerator)), new BigInteger(String.valueOf(denominator)));
    }

    public IntFraction(BigInteger numerator, BigInteger denominator) {
        BigInteger gcd = gcd(numerator.abs(), denominator.abs());
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);
        if (denominator.compareTo(BigInteger.ZERO) < 0) {
            denominator = denominator.negate();
            numerator = numerator.negate();
        }
        this.numerator = numerator;
        this.denominator = denominator;
        this.value = value();
    }

    public IntFraction(BigDecimal bd) {
        this.value = bd.setScale(10, RoundingMode.HALF_UP);
        BigInteger numerator = value.multiply(BigDecimal.TEN.pow(10)).toBigInteger();
        BigInteger denominator = BigInteger.TEN.pow(10);
        BigInteger gcd = gcd(numerator.abs(), denominator.abs());
        this.numerator = numerator.divide(gcd);
        this.denominator = denominator.divide(gcd);
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        if (b.compareTo(BigInteger.ZERO) == 0) return a;
        else return gcd(b, a.mod(b));
    }

    public BigDecimal value() {
        if (denominator.intValue() == 0) {
            throw new ArithmeticException("Error: Divide by zero.");
        } else {
            return new BigDecimal(String.valueOf(numerator.doubleValue() / denominator.doubleValue()));
        }
    }

    @Override
    public IntFraction negate() {
        return new IntFraction(numerator.negate(), denominator);
    }

    @Override
    public IntFraction round_to_n_dp(int n) {
        return new Decimal(value).round_to_n_dp(n).convert_to_fraction();
    }


    IntFraction to_positive() {
        BigInteger top = numerator.abs();
        BigInteger bot = denominator.abs();
        return new IntFraction(top, bot);
    }

    @Override
    public IntFraction congruent() {
        return this;
    }


    @Override
    public String toString() {

        if (isInteger()) {
            if (isZero()) {
                return 0 + "";
            } else return numerator.divide(denominator) + "";
        } else if (value().doubleValue() < 0) {
            return String.format("-%d/%d", this.to_positive().numerator, this.to_positive().denominator);
        } else return numerator + "/" + denominator;
    }

}
