package com.truebubo.maniflow.Currency;

import java.math.BigDecimal;

public interface CurrencyConverter {
    /**
     * How much of the {to currency} can we buy for 1 {from currency}
     * @param from Currency we sell
     * @param to Currency we buy
     * @return exchange rate
     */
    Double convert(CurrencyDesignation from, CurrencyDesignation to);
}
