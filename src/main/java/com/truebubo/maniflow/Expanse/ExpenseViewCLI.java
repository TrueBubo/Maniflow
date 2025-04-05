package com.truebubo.maniflow.Expanse;


import java.util.List;

import static java.util.stream.IntStream.range;

/// CLI Frontend for expense portion of the application
public class ExpenseViewCLI {
    private final ExpenseService expenseService;

    public ExpenseViewCLI(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /// Displays the list of all the expenses for the user
    void showExpenses() {
        List<Expense> expenses = expenseService.getExpenses();
        range(0, expenses.size()).forEach(idx -> {
            final var expense = expenses.get(idx);
            System.out.println(idx + 1 + ". " + expense.value() + expense.currencyDesignation());
        });
    }
}
