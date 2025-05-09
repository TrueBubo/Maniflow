package com.truebubo.maniflow.money.CurrencyTypes;

import com.truebubo.maniflow.money.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

/// Czech crown
public final class CZK extends Money<CZK> {
    /// Small unit of currency
    public final static CZK Haler = new CZK(BigDecimal.valueOf(0.01));
    /// Big unit of currency
    public final static CZK Crown = new CZK(BigDecimal.ONE);

    private CZK(@NonNull BigDecimal amount) {
        super(amount, CurrencyDesignation.CZK);
    }

    /// @param amount How much of a currency do we own
    @Override
    public CZK make(BigDecimal amount) {
        return new CZK(amount);
    }
}
