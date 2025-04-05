package com.truebubo.maniflow.Income;

import com.truebubo.maniflow.Money.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

/// Information about income
/// @param value How much is the new income
/// @param currencyDesignation What currency the income is in
/// @param created Timestamp of when the new income was approved
public record Income(@NonNull BigDecimal value, @NonNull CurrencyDesignation currencyDesignation,  Instant created) {
}
