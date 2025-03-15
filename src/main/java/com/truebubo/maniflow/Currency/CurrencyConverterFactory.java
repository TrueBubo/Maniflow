package com.truebubo.maniflow.Currency;

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
