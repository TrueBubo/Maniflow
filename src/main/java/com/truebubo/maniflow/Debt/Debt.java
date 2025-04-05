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
}
