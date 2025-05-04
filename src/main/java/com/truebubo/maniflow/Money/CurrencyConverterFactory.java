package com.truebubo.maniflow.Money;

import org.springframework.lang.NonNull;

/// Used for providing a way for all the applications to use the same converter
public class CurrencyConverterFactory {
    private static CurrencyConverter converter;

    /// Lazily returns a currency converter
    /// @return Reused or new converter
    public static CurrencyConverter getConverter() {
        if (converter == null) {
            converter = PolygonCurrencyConverter.get();
        }
        return converter;
    }

    /// Sets a custom converter from outside
    /// @param converter Custom converter
    public static void setConvertor(@NonNull CurrencyConverter converter) {
        CurrencyConverterFactory.converter = converter;
    }
}
