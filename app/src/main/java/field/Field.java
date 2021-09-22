package field;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

import basicOps.Addition;
import basicOps.Division;
import basicOps.Multiplication;
import basicOps.Subtraction;
import function.Logarithms;

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
    public int compareTo(@NonNull Field f) {

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
        return Logarithms.exp(this, p);
    }

}
