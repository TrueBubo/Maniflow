package com.truebubo.maniflow.Currency;

import com.truebubo.maniflow.Currency.CurrencyTypes.EUR;
import com.truebubo.maniflow.Currency.CurrencyTypes.USD;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {
    final USD dollarAmount = USD.Dollar;
    final EUR euroAmount = EUR.Euro;

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

    @Test
    void to() throws ConversionFailedException {
        CurrencyConverterFactory.setConvertor(DummyConverter.get());
        var twoEurosInDollars = euroAmount.multiply(2).to(USD.Dollar).amount();
        assertEquals(BigDecimal.valueOf(2.2), twoEurosInDollars);
    }
}