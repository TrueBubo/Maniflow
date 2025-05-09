package com.truebubo.maniflow.expense;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;

/// Handles business logic behind expenses
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    /// Service for expenses
    ///
    /// @param expenseRepository Repository for data about expenses
    public ExpenseService(@NonNull ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /// Gets the list of all the expenses saved by the user
    ///
    /// @return All the expenses saved
    public List<Expense> getExpenses() {
        return expenseRepository.getExpenses();
    }

    /// Saves the expense
    ///
    /// @param expense Information about the expense to be added
    public void addExpense(@NonNull Expense expense) {
        expenseRepository.saveExpense(expense);
    }

    /// Change the value on the expense with given id
    ///
    /// @param id        ID identifying the expense. It is the left value displayed when calling showExpenses
    /// @param newAmount Will update the value to this value. If set to zero. The value will be removed
    public void changeExpense(int id, @NonNull BigDecimal newAmount) {
        expenseRepository.changeExpense(id, newAmount);
    }
}
