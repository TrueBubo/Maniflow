package com.truebubo.maniflow.Income;

import com.truebubo.maniflow.Money.CurrencyDesignation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;

class IncomeViewCLITest {
    IncomeService incomeService;

    @BeforeEach
    public void setUp() {
        incomeService = Mockito.mock(IncomeService.class);
    }

    @Test
    void showIncomes() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        var mockIncomes = List.of(
                new Income(BigDecimal.valueOf(60000), CurrencyDesignation.CZK, Instant.now()),
                new Income(BigDecimal.valueOf(5000), CurrencyDesignation.EUR, Instant.now())
        );
        when(incomeService.getIncomes()).thenReturn(mockIncomes);
        new IncomeViewCLI(incomeService).showIncomes();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertEquals("60000CZK\n5000EUR\n", output);

    }
}