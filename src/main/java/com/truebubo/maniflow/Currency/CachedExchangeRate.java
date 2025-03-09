package com.truebubo.maniflow.Currency;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record CachedExchangeRate(BigDecimal rate, ZonedDateTime time) {
}
