package field;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Real extends Field {

    public abstract BigDecimal value();

    @Override
    public abstract Real negate();

    @Override
    public abstract Real round_to_n_dp(int n);

    @Override
    public Real real() {
        return this;
    }

    @Override
    public Real imag() {
        return new IntFraction(0);
    }

    @Override
    public Decimal modulus() {
        return new Decimal(value().abs());
    }

    public Decimal sqrt() {
        return new Decimal(Math.sqrt(this.value().doubleValue()));
    }

    public boolean isInteger() {
        return value().setScale(9, RoundingMode.HALF_UP).remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean lessThan(Real operand) {
        return this.compareTo(operand) < 0;
    }

    public boolean greaterThan(Real operand) {
        return this.compareTo(operand) > 0;
    }

    @Override
    public boolean isZero() {
        return value().setScale(9, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) == 0;
    }

}
