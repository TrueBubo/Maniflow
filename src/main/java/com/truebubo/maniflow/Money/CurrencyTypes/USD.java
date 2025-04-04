package com.truebubo.maniflow.Money.CurrencyTypes;

import com.truebubo.maniflow.Money.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public final class USD extends Money<USD> {
    public final static USD Cent = new USD(BigDecimal.valueOf(0.01));
    public final static USD Dollar = new USD(BigDecimal.ONE);

    private USD(@NonNull BigDecimal amount) {
        super(amount, CurrencyDesignation.USD);
    }

    /// @param amount How much of a currency do we own
    @Override
    public USD make(BigDecimal amount) {
        return new USD(amount);
    }
}
