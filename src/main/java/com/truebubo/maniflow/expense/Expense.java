package com.truebubo.maniflow.expense;

import com.truebubo.maniflow.money.CurrencyDesignation;
import com.truebubo.maniflow.money.MoneyExchange;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

/// Information about expense
///
/// @param value               How much is the new expense
/// @param currencyDesignation What currency the expense is in
/// @param created             Timestamp of when the new expense was approved
/// @param repeatsAfterDays    This will be automatically added after again after this many days, if does not repeat the value should be -1
public record Expense(@NonNull BigDecimal value, @NonNull CurrencyDesignation currencyDesignation,
                      @NonNull Instant created, @Nullable Integer repeatsAfterDays) implements MoneyExchange {
    /// Constructor without repeating
    ///
    /// @param value               How much is the new expense
    /// @param currencyDesignation What currency the expense is in
    /// @param created             Timestamp of when the new expense was approved
    public Expense(@NonNull BigDecimal value, @NonNull CurrencyDesignation currencyDesignation, @NonNull Instant created) {
        this(value, currencyDesignation, created, null);
    }
}