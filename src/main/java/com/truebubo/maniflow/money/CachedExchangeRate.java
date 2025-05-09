package com.truebubo.maniflow.money;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/// Used for not querying the database when we already have a recent data entry
///
/// @param rate How more valuable is one currency compared to other
/// @param time Where was this result set
public record CachedExchangeRate(@NonNull BigDecimal rate, @NonNull ZonedDateTime time) {
}
