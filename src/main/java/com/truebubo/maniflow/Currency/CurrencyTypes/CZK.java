package com.truebubo.maniflow.Currency.CurrencyTypes;

import com.truebubo.maniflow.Currency.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public final class CZK extends Currency<CZK> {
    public final static com.truebubo.maniflow.Currency.CurrencyTypes.CZK Haler = new com.truebubo.maniflow.Currency.CurrencyTypes.CZK(BigDecimal.valueOf(0.01));
    public final static com.truebubo.maniflow.Currency.CurrencyTypes.CZK Crown = new com.truebubo.maniflow.Currency.CurrencyTypes.CZK(BigDecimal.ONE);

    private CZK(@NonNull BigDecimal amount) {
        super(amount, CurrencyDesignation.CZK);
    }

    /// @param amount How much of a currency do we own
    @Override
    public CZK make(BigDecimal amount) {
        return new CZK(amount);
    }
}
