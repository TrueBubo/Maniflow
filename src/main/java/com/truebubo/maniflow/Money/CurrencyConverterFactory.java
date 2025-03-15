package com.truebubo.maniflow.Money;

/// Used for providing a way for all the applications to use the same converter
public class CurrencyConverterFactory {
    private static CurrencyConverter converter;

    public static CurrencyConverter getConverter() {
        if (converter == null) {
            converter = PolygonCurrencyConverter.get();
        }
        return converter;
    }

    public static void setConvertor(CurrencyConverter converter) {
        CurrencyConverterFactory.converter = converter;
    }
}
