package com.truebubo.maniflow.Currency;

import com.truebubo.maniflow.Currency.CurrencyTypes.USD;
import org.springframework.lang.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;

/**
 * Holds the value of money
 * @param <T> Currency itself. Used for making operation only between the same currency
 */
public abstract sealed class Currency<T extends Currency<T>> permits Currency.CZK, Currency.EUR, Currency.GBP, USD {
    private final @NonNull CurrencyDesignation designation;
    private final @NonNull BigDecimal amount;

    protected Currency(@NonNull BigDecimal amount, @NonNull CurrencyDesignation designation) {
        this.amount = amount;
        this.designation = designation;
    }

    public final T to(@NonNull CurrencyDesignation toCurrency) throws ConversionFailedException {
        var convertor = CurrencyConverterFactory.getConverter();
        double exchangeRate = convertor.convert(designation, toCurrency).orElseThrow(() -> new ConversionFailedException(String.format("Could not convert from %s to %s. Please check your internet connection", designation, toCurrency)));
        return multiply(exchangeRate);
    }

    public final @NonNull BigDecimal amount() {
        return amount;
    }

    public final @NonNull String toString() {
        return amount.toPlainString() + " " + designation;
    }

    private T make(@NonNull BigDecimal amount) {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        var currency = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        try {
            return currency.getDeclaredConstructor(BigDecimal.class).newInstance(amount);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exception){
            System.err.println("Currency classes must implement constructor taking one BigDecimal value");
            return null; // Should never happen
        }
    }

    public final T add(@NonNull T other) {
        return make(amount.add(other.amount()));
    }

    public final T multiply(@NonNull BigDecimal multiplier) {
        return make(amount.multiply(multiplier));
    }

    public final T multiply(long multiplier) {
        return multiply(BigDecimal.valueOf(multiplier));
    }

    public final T multiply(double multiplier) {
        return multiply(BigDecimal.valueOf(multiplier));
    }


    public final static class EUR extends Currency<EUR> {
        public final static EUR Cent = new EUR(BigDecimal.valueOf(0.01));
        public final static EUR Euro = new EUR(BigDecimal.ONE);
        private EUR(@NonNull BigDecimal amount) {
            super(amount, CurrencyDesignation.EUR);
        }
    }

    public final static class CZK extends Currency<CZK> {
        public final static CZK Haler = new CZK(BigDecimal.valueOf(0.01));
        public final static CZK Crown = new CZK(BigDecimal.ONE);
        private CZK(@NonNull BigDecimal amount) {
            super(amount, CurrencyDesignation.CZK);
        }
    }

    public final static class GBP extends Currency<GBP> {
        public final static GBP Penny = new GBP(BigDecimal.valueOf(0.01));
        public final static GBP Pound = new GBP(BigDecimal.ONE);
        private GBP(@NonNull BigDecimal amount) {
            super(amount, CurrencyDesignation.GBP);
        }
    }
}
