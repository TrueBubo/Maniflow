package com.truebubo.maniflow.income;

import com.truebubo.maniflow.money.CurrencyDesignation;
import com.truebubo.maniflow.money.MoneyExchange;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

/// Information about income
///
/// @param value               How much is the new income
/// @param currencyDesignation What currency the income is in
/// @param created             Timestamp of when the new income was approved
/// @param repeatsAfterDays    This will be automatically added after again after this many days
public record Income(@NonNull BigDecimal value, @NonNull CurrencyDesignation currencyDesignation,
                     @NonNull Instant created, @Nullable Integer repeatsAfterDays) implements MoneyExchange {
    /// Constructor without repeating
    ///
    /// @param value               How much is the new income
    /// @param currencyDesignation What currency the income is in
    /// @param created             Timestamp of when the new income was approved
    public Income(@NonNull BigDecimal value, @NonNull CurrencyDesignation currencyDesignation, @NonNull Instant created) {
        this(value, currencyDesignation, created, null);
    }
}
