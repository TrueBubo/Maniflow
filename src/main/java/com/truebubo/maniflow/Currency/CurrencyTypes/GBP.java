package com.truebubo.maniflow.Currency.CurrencyTypes;

import com.truebubo.maniflow.Currency.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public final class GBP extends Currency<GBP> {
    public final static com.truebubo.maniflow.Currency.CurrencyTypes.GBP Penny = new com.truebubo.maniflow.Currency.CurrencyTypes.GBP(BigDecimal.valueOf(0.01));
    public final static com.truebubo.maniflow.Currency.CurrencyTypes.GBP Pound = new com.truebubo.maniflow.Currency.CurrencyTypes.GBP(BigDecimal.ONE);

    private GBP(@NonNull BigDecimal amount) {
        super(amount, CurrencyDesignation.GBP);
    }

    /// @param amount How much of a currency do we own
    @Override
    public GBP make(BigDecimal amount) {
        return new GBP(amount);
    }
}
