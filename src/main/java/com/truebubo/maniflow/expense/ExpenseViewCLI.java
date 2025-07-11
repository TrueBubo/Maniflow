package com.truebubo.maniflow.expense;


import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.IntStream.range;

/// CLI Frontend for expense portion of the application
public class ExpenseViewCLI {
    private final ExpenseService expenseService;

    /// CLI front-end for expenses
    ///
    /// @param expenseService service for accessing expense back-end
    public ExpenseViewCLI(@NonNull ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /// Displays the list of all the expenses for the user
    public void showExpenses() {
        List<Expense> expenses = expenseService.get();
        range(0, expenses.size()).forEach(idx -> {
            final var expense = expenses.get(idx);
            System.out.println(idx + 1 + ". " + expense.value() + expense.currencyDesignation());
        });
    }

    /// Saves the expense
    ///
    /// @param expense Information about the expense to be added
    public void addExpense(@NonNull Expense expense) {
        expenseService.add(expense);
    }

    /// Change the value on the expense with given id
    ///
    /// @param id        ID identifying the expense. It is the left value displayed when calling showExpenses
    /// @param newAmount Will update the value to this value. If set to zero. The value will be removed
    public void changeExpense(int id, @NonNull BigDecimal newAmount) {
        expenseService.change(id, newAmount);
    }
}
