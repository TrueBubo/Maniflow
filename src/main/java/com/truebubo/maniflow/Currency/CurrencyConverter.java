package com.truebubo.maniflow.Currency;

import java.math.BigDecimal;

public interface CurrencyConverter {
    BigDecimal convert(CurrencyDesignation from, CurrencyDesignation to);
}
