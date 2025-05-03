package com.truebubo.maniflow.Stats;

import com.truebubo.maniflow.Debt.Debt;
import com.truebubo.maniflow.Debt.DebtService;
import com.truebubo.maniflow.Expense.ExpenseService;
import com.truebubo.maniflow.Income.Income;
import com.truebubo.maniflow.Income.IncomeService;
import com.truebubo.maniflow.Money.CurrencyDesignation;
import com.truebubo.maniflow.Stock.Stock;
import com.truebubo.maniflow.Stock.StockService;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

/// Handles business logic behind income
public class StatsService {
    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final StockService stockService;
    private final DebtService debtService;

    public StatsService(IncomeService incomeService,
                        ExpenseService expenseService,
                        StockService stockService,
                        DebtService debtService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
        this.stockService = stockService;
        this.debtService = debtService;
    }

    /// Returns financial stats for the user
    public Stats getMoneyStats() {
        final Instant now = Instant.now();
        Map<CurrencyDesignation, BigDecimal> ownsMoneyPerCurrency = incomeService.getIncomes().parallelStream().collect(
                groupingBy(Income::currencyDesignation,
                        reducing(BigDecimal.ZERO, (Income income) -> income.value().multiply(BigDecimal.valueOf(
                                income.repeatsAfterDays() < 1 ? 1 // Does not repeat. Designated value is -1, but this handles everything invalid
                                        : income.created().until(now, ChronoUnit.DAYS) / income.repeatsAfterDays() + 1)
                        ), BigDecimal::add)));

        expenseService.getExpenses().forEach(expense ->
                ownsMoneyPerCurrency.put(expense.currencyDesignation(),
                        ownsMoneyPerCurrency.getOrDefault(expense.currencyDesignation(), BigDecimal.ZERO)
                                .subtract(expense.value()
                                )));

        Map<String, BigDecimal> ownsStocksPerTicket = stockService.getStockHoldings().parallelStream().collect(
                groupingBy(Stock::ticket, reducing(BigDecimal.ZERO, Stock::volume, BigDecimal::add)
                ));

        Map<CurrencyDesignation, BigDecimal> owesMoneyPerCurrency = debtService.getDebts().parallelStream().collect(
                groupingBy(Debt::currencyDesignation, reducing(BigDecimal.ZERO, Debt::value, BigDecimal::add))
        );

        return new Stats(ownsMoneyPerCurrency, ownsStocksPerTicket, owesMoneyPerCurrency);
    }
}
