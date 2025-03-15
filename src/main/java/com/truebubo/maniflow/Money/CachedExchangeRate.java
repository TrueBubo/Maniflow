package com.truebubo.maniflow.Money;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/// Used for not querying the database when we already have a recent data entry
public record CachedExchangeRate(BigDecimal rate, ZonedDateTime time) {
}
