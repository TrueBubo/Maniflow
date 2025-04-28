package com.truebubo.maniflow.Income;

import com.truebubo.maniflow.Database.MongoDb;
import com.truebubo.maniflow.Money.CurrencyDesignation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class IncomeServiceTest {
    IncomeService incomeService = new IncomeService(new MongoIncomeRepository(MongoDb.getInstance()));

    @Test
    void getIncomes() {
        incomeService.getIncomes().forEach(System.out::println);
    }

    @Test
    void addIncome() {
        incomeService.addIncome(new Income(BigDecimal.TEN, CurrencyDesignation.CZK, Instant.now()));
    }

    @Test
    void changeIncome() {
        incomeService.changeIncome(0, BigDecimal.ZERO);
    }
}