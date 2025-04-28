package com.truebubo.maniflow.Debt;

import com.truebubo.maniflow.Money.CurrencyDesignation;
import com.truebubo.maniflow.UtilTests.UtilTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DebtViewCLITest {
    private DebtService debtService;

    @BeforeEach
    void setUp() {
        debtService = Mockito.mock(DebtService.class);
    }

    @Test
    void showDebts() {
        String output = UtilTests.capturePrintedOutput(() -> {
            var mockDebts = List.of(
                    new Debt(BigDecimal.valueOf(60000), CurrencyDesignation.CZK, BigDecimal.valueOf(3.2), Instant.now()),
                    new Debt(BigDecimal.valueOf(5000), CurrencyDesignation.EUR, BigDecimal.valueOf(5.1), Instant.now())
            );
            when(debtService.getDebts()).thenReturn(mockDebts);
            new DebtViewCLI(debtService).showDebts();
        });

        assertEquals("1. 60000CZK 3.2%\n2. 5000EUR 5.1%\n", output);
    }
}