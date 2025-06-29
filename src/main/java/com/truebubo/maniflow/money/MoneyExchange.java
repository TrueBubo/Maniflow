package com.truebubo.maniflow.money;

import java.math.BigDecimal;
import java.time.Instant;

public interface MoneyExchange {
    BigDecimal value();
    CurrencyDesignation currencyDesignation();
    Instant created();
    Integer repeatsAfterDays();
}
