package com.truebubo.maniflow.expense;

import com.truebubo.maniflow.money.CurrencyDesignation;
import com.truebubo.maniflow.util_tests.UtilTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ExpenseViewCLITest {
    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        expenseService = Mockito.mock(ExpenseService.class);
    }

    @Test
    void showExpenses() {

        String output = UtilTests.capturePrintedOutput(() -> {
            var mockExpenses = List.of(
                    new Expense(BigDecimal.valueOf(60000), CurrencyDesignation.CZK, Instant.now()),
                    new Expense(BigDecimal.valueOf(5000), CurrencyDesignation.EUR, Instant.now())
            );

            when(expenseService.getExpenses()).thenReturn(mockExpenses);
            new ExpenseViewCLI(expenseService).showExpenses();
        });

        assertEquals("1. 60000CZK\n2. 5000EUR\n", output);
    }
}