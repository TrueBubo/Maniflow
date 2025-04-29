package com.truebubo.maniflow.Stats;

import com.truebubo.maniflow.Debt.DebtService;
import com.truebubo.maniflow.Expense.Expense;
import com.truebubo.maniflow.Expense.ExpenseService;
import com.truebubo.maniflow.Income.IncomeService;
import com.truebubo.maniflow.Stock.StockService;

public class StatsViewCLI {
    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final StockService stockService;
    private final DebtService debtService;

    public StatsViewCLI(IncomeService incomeService,
                        ExpenseService expenseService,
                        StockService stockService,
                        DebtService debtService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
        this.stockService = stockService;
        this.debtService = debtService;
    }

    public void showMoneyStats() {

    }
}
