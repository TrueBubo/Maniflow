package com.truebubo.maniflow.money.CurrencyTypes;

import com.truebubo.maniflow.money.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

/// British pound
public final class GBP extends Money<GBP> {
    /// Small unit of currency
    public final static GBP Penny = new GBP(BigDecimal.valueOf(0.01));
    /// Large unit of currency
    public final static GBP Pound = new GBP(BigDecimal.ONE);

    private GBP(@NonNull BigDecimal amount) {
        super(amount, CurrencyDesignation.GBP);
    }

    /// @param amount How much of a currency do we own
    @Override
    public GBP make(BigDecimal amount) {
        return new GBP(amount);
    }
}
