package field;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Decimal extends Real {

    private BigDecimal value;

    public Decimal(double value) {
        this.value = new BigDecimal(String.valueOf(value));
    }

    public Decimal(String value) {
        this.value = new BigDecimal(String.valueOf(value));
    }

    public Decimal(BigDecimal value) {
        this.value = value;
    }

    @Override
    public Decimal congruent() {
        return this;
    }

    @Override
    public Decimal negate() {
        return new Decimal(value.negate());
    }

    public boolean isSmall() {
        return (value.stripTrailingZeros().precision() < 6);
    }

    public IntFraction convert_to_fraction() {
        return new IntFraction(this.value);
    }

    @Override
    public String toString() {

        if (isZero()) return "0";
        else if (isInteger()) return round_to_n_dp(0).value + "";
        else return round_to_n_dp(8).value.stripTrailingZeros() + "";
    }

    @Override
    public BigDecimal value() {
        return value;
    }


    @Override
    public Decimal round_to_n_dp(int n) {
        return new Decimal(value.setScale(n, RoundingMode.HALF_UP));
    }

}
