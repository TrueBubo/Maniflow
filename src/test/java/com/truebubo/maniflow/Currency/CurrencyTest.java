package com.truebubo.maniflow.Currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {
    final Currency.USD dollarAmount = Currency.USD.Dollar;
    final Currency.EUR euroAmount = Currency.EUR.Euro;

    @Test
    void amount() {
        assertEquals(BigDecimal.ONE, euroAmount.amount());
    }

    @Test
    void testToString() {
        assertEquals("1 USD", dollarAmount.toString());
    }

    @Test
    void add() {
        assertEquals(BigDecimal.TWO, euroAmount.add(euroAmount).amount());
    }

    @Test
    void multiply() {
        assertEquals(BigDecimal.valueOf(3.2), euroAmount.multiply(3.2).amount());
        assertEquals(BigDecimal.valueOf(256), euroAmount.multiply(256).amount());
        assertEquals(BigDecimal.ZERO, euroAmount.multiply(BigDecimal.ZERO).amount());
    }
}