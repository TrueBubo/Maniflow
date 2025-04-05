package com.truebubo.maniflow.Expanse;

import com.truebubo.maniflow.Money.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

/// Information about income
///
/// @param value               How much is the new income
/// @param currencyDesignation What currency the income is in
/// @param created             Timestamp of when the new income was approved
/// @param repeatsAfterDays    This will be automatically added after again after this many days
public record Expense(@NonNull BigDecimal value, @NonNull CurrencyDesignation currencyDesignation,
                      @NonNull Instant created, int repeatsAfterDays) {
    public Expense(@NonNull BigDecimal value, @NonNull CurrencyDesignation currencyDesignation, @NonNull Instant created) {
        this(value, currencyDesignation, created, -1);
    }
}