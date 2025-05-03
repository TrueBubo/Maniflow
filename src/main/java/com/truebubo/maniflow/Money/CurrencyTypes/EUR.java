package com.truebubo.maniflow.Money.CurrencyTypes;

import com.truebubo.maniflow.Money.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

/// Euro
public final class EUR extends Money<EUR> {
    /// Small unit of currency
    public final static com.truebubo.maniflow.Money.CurrencyTypes.EUR Cent = new com.truebubo.maniflow.Money.CurrencyTypes.EUR(BigDecimal.valueOf(0.01));
    /// Large unit of currency
    public final static com.truebubo.maniflow.Money.CurrencyTypes.EUR Euro = new com.truebubo.maniflow.Money.CurrencyTypes.EUR(BigDecimal.ONE);

    private EUR(@NonNull BigDecimal amount) {
        super(amount, CurrencyDesignation.EUR);
    }

    /// @param amount How much of a currency do we own
    @Override
    public EUR make(BigDecimal amount) {
        return new EUR(amount);
    }
}
