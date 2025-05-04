package com.truebubo.maniflow.Debt;

import com.truebubo.maniflow.Money.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

/// Information about debt
///
/// @param value               How much is the new debt
/// @param currencyDesignation What currency the debt is in
/// @param yearlyInterest      Interest rate on the debt
/// @param created             Timestamp of when the new debt was approved
public record Debt(@NonNull BigDecimal value, @NonNull CurrencyDesignation currencyDesignation,
                   @NonNull BigDecimal yearlyInterest, @NonNull Instant created) {
    final static long secondsInYear = 31557600;

    /// How much is the debt now including the interest that accumulated since last updates
    ///
    /// @return Value with interest
    public BigDecimal getValueWithInterest() {
        final var yearsFromDebt = (double) (Instant.now().getEpochSecond() - this.created().getEpochSecond()) / secondsInYear;
        return this.value().multiply(
                BigDecimal.valueOf(1 + yearsFromDebt));
    }
}
