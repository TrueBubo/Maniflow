package com.truebubo.maniflow.Currency;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyConvertorTest {
    CurrencyConverter convertor = new PolygonCurrencyConvertor();
    @Test
    void convert() {
        convertor.convert(CurrencyDesignation.EUR, CurrencyDesignation.USD);
        assertTrue(true);
    }
}