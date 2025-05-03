package com.truebubo.maniflow.Money;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/// Used for not querying the database when we already have a recent data entry
/// @param rate How more valuable is one currency compared to other
/// @param time Where was this result set
public record CachedExchangeRate(BigDecimal rate, ZonedDateTime time) {
}
