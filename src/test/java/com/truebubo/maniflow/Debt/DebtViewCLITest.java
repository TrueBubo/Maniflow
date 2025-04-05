package com.truebubo.maniflow.Debt;

import com.truebubo.maniflow.Expense.Expense;
import com.truebubo.maniflow.Expense.ExpenseViewCLI;
import com.truebubo.maniflow.Money.CurrencyDesignation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        var mockDebts = List.of(
                new Debt(BigDecimal.valueOf(60000), CurrencyDesignation.CZK, BigDecimal.valueOf(3.2), Instant.now()),
                new Debt(BigDecimal.valueOf(5000), CurrencyDesignation.EUR, BigDecimal.valueOf(5.1), Instant.now())
        );
        when(debtService.getDebts()).thenReturn(mockDebts);
        new DebtViewCLI(debtService).showDebts();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertEquals("1. 60000CZK 3.2%\n2. 5000EUR 5.1%\n", output);
    }
}