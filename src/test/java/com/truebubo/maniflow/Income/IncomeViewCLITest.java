package com.truebubo.maniflow.Income;

import com.truebubo.maniflow.Money.CurrencyDesignation;
import com.truebubo.maniflow.UtilTests.UtilTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;

class IncomeViewCLITest {
    private IncomeService incomeService;

    @BeforeEach
    public void setUp() {
        incomeService = Mockito.mock(IncomeService.class);
    }

    @Test
    void showIncomes() {
        String output = UtilTests.capturePrintedOutput(() -> {
            var mockIncomes = List.of(
                    new Income(BigDecimal.valueOf(60000), CurrencyDesignation.CZK, Instant.now()),
                    new Income(BigDecimal.valueOf(5000), CurrencyDesignation.EUR, Instant.now())
            );
            when(incomeService.getIncomes()).thenReturn(mockIncomes);
            new IncomeViewCLI(incomeService).showIncomes();
        });


        assertEquals("1. 60000CZK\n2. 5000EUR\n", output);

    }
}