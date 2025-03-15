package com.truebubo.maniflow.Money;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CurrencyDesignationTest {
    @Test
    void fromString() {
        assertEquals(CurrencyDesignation.USD, CurrencyDesignation.fromString("USD"));
        assertEquals(CurrencyDesignation.CZK, CurrencyDesignation.fromString("czk"));
        assertThrowsExactly(UnsupportedCurrencyException.class, () -> CurrencyDesignation.fromString("NonExistingCurrency"));
    }
}