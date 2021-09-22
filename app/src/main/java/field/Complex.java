package field;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class Complex extends Field {

    public final Real re;
    public final Real im;


    public Complex(int re, int im) {
        this.re = new IntFraction(re);
        this.im = new IntFraction(im);
    }

    public Complex(double re, double im) {
        this.re = new Decimal(re);
        this.im = new Decimal(im);
    }

    public Complex(Real re, Real im) {
        this.re = re;
        this.im = im;
    }


    private String realPart() {
        if (re.isZero()) {
            return "";
        } else return re.toString();
    }


    @SuppressLint("DefaultLocale")
    private String modulusImaginaryPart() {

        if (im.isInteger()) {
            if (im.isZero()) {
                return "";
            } else if (im.modulus().compareTo(BigDecimal.ONE) == 0) {
                return "i";
            } else return String.format("%si", im.modulus().toString());
        } else if (im instanceof IntFraction) {
            IntFraction fra_im = (IntFraction) im;
            if (fra_im.to_positive().numerator.compareTo(BigInteger.ONE) == 0) {
                return String.format("i/%d", fra_im.to_positive().denominator);
            } else
                return String.format("%di/%d", fra_im.to_positive().numerator, fra_im.to_positive().denominator);
        } else {
            Decimal dec_im = (Decimal) im;
            if (dec_im.modulus().compareTo(1) == 0) {
                return "i";
            } else {
                return String.format("%si", im.modulus());
            }
        }
    }


    @Override
    public String toString() {
        if (isZero()) return "0";
        else if (im.compareTo(0) < 0) {
            return String.format("%s-%s", realPart(), modulusImaginaryPart());
        } else if (im.isZero()) {
            return re.toString();
        } else if (re.isZero()) {
            return modulusImaginaryPart();
        } else return String.format("%s+%s", realPart(), modulusImaginaryPart());
    }

    @Override
    public boolean isZero() {
        return re.isZero() && im.isZero();
    }


    @Override
    public Complex negate() {
        return new Complex(re.negate(), im.negate());
    }


    @Override
    public Decimal modulus() {
        return re.mul(re).add(im.mul(im)).modulus().sqrt();
    }

    @Override
    public Real real() {
        return re;
    }

    @Override
    public Real imag() {
        return im;
    }

    @Override
    public Complex congruent() {
        return new Complex(re, im.negate());
    }

    @Override
    public Complex round_to_n_dp(int n) {
        return new Complex(re.round_to_n_dp(n), im.round_to_n_dp(n));
    }

}
