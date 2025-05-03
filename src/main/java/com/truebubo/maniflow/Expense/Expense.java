package com.truebubo.maniflow.Expense;

import com.truebubo.maniflow.Money.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

/// Information about expense
///
/// @param value               How much is the new expense
/// @param currencyDesignation What currency the expense is in
/// @param created             Timestamp of when the new expense was approved
/// @param repeatsAfterDays    This will be automatically added after again after this many days, if does not repeat the value should be -1
public record Expense(@NonNull BigDecimal value, @NonNull CurrencyDesignation currencyDesignation,
                      @NonNull Instant created, int repeatsAfterDays) {
    public Expense(@NonNull BigDecimal value, @NonNull CurrencyDesignation currencyDesignation, @NonNull Instant created) {
        this(value, currencyDesignation, created, -1);
    }
}