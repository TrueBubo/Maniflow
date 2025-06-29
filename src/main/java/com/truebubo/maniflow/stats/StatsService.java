package com.truebubo.maniflow.stats;

import com.truebubo.maniflow.debt.Debt;
import com.truebubo.maniflow.debt.DebtService;
import com.truebubo.maniflow.expense.ExpenseService;
import com.truebubo.maniflow.income.Income;
import com.truebubo.maniflow.income.IncomeService;
import com.truebubo.maniflow.money.CurrencyDesignation;
import com.truebubo.maniflow.stock.Stock;
import com.truebubo.maniflow.stock.StockService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

/// Handles business logic behind statistics
@Service
public class StatsService {
    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final StockService stockService;
    private final DebtService debtService;

    /// Initializes stats service with other services
    ///
    /// @param incomeService  Income service
    /// @param expenseService Expense service
    /// @param stockService   Stock service
    /// @param debtService    Debt service
    public StatsService(@NonNull IncomeService incomeService,
                        @NonNull ExpenseService expenseService,
                        @NonNull StockService stockService,
                        @NonNull DebtService debtService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
        this.stockService = stockService;
        this.debtService = debtService;
    }

    /// Returns financial stats for the user
    ///
    /// @return Stats for the user
    public Stats getMoneyStats() {
        final Instant now = Instant.now();
        Map<CurrencyDesignation, BigDecimal> ownsMoneyPerCurrency = incomeService.get().stream().collect(
                groupingBy(Income::currencyDesignation,
                        reducing(BigDecimal.ZERO, (Income income) -> income.value().multiply(BigDecimal.valueOf(
                                        income.repeatsAfterDays() == null ? 1
                                                : income.created().until(now, ChronoUnit.DAYS) / income.repeatsAfterDays() + 1
                                )), BigDecimal::add
                        )
                )
        );

        expenseService.get().forEach(expense ->
                ownsMoneyPerCurrency.put(expense.currencyDesignation(),
                        ownsMoneyPerCurrency.getOrDefault(expense.currencyDesignation(), BigDecimal.ZERO)
                                .subtract(expense.value().multiply(BigDecimal.valueOf(
                                                expense.repeatsAfterDays() == null ? 1
                                                        : expense.created().until(now, ChronoUnit.DAYS) / expense.repeatsAfterDays() + 1
                                        )
                                ))
                )
        );

        Map<String, BigDecimal> ownsStocksPerTicket = stockService.getStockHoldings().stream().collect(
                groupingBy(Stock::ticket, reducing(BigDecimal.ZERO, Stock::volume, BigDecimal::add)
                ));

        Map<CurrencyDesignation, BigDecimal> owesMoneyPerCurrency = debtService.getDebts().stream().collect(
                groupingBy(Debt::currencyDesignation, reducing(BigDecimal.ZERO, Debt::value, BigDecimal::add))
        );

        return new Stats(ownsMoneyPerCurrency, ownsStocksPerTicket, owesMoneyPerCurrency);
    }
}
