package com.truebubo.maniflow.Currency.CurrencyTypes;

import com.truebubo.maniflow.Currency.ConversionFailedException;
import com.truebubo.maniflow.Currency.CurrencyConverterFactory;
import com.truebubo.maniflow.Currency.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;

/**
 * Holds the value of money
 * @param <T> Currency itself. Used for making operation only between the same currency
 */
public abstract sealed class Currency<T extends Currency<T>> permits CZK, EUR, GBP, USD {
    private final @NonNull CurrencyDesignation designation;
    private final @NonNull BigDecimal amount;

    protected Currency(@NonNull BigDecimal amount, @NonNull CurrencyDesignation designation) {
        this.amount = amount;
        this.designation = designation;
    }

    public final CurrencyDesignation designation() {return designation;}

    public final <ToCurrency extends Currency<ToCurrency>> ToCurrency to(@NonNull ToCurrency toCurrencyInstance) throws ConversionFailedException {
        var convertor = CurrencyConverterFactory.getConverter();
        var toDesignation = toCurrencyInstance.designation();
        double exchangeRate = convertor.convert(designation, toDesignation).orElseThrow(
                () -> new ConversionFailedException(
                        String.format("Could not convert from %s to %s. Please check your internet connection", designation, toDesignation))
        );
        return toCurrencyInstance.make(amount.multiply(BigDecimal.valueOf(exchangeRate)));
    }

    public final @NonNull BigDecimal amount() {
        return amount;
    }

    public final @NonNull String toString() {
        return amount.toPlainString() + " " + designation;
    }

    /// Used to force children to give us their instance
    /// @param amount How much of a currency do we own
    public abstract T make(BigDecimal amount);

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
}