package com.truebubo.maniflow.Currency;

import java.math.BigDecimal;

public interface Converter {
    BigDecimal convert(CurrencyDesignation from, CurrencyDesignation to);
}
