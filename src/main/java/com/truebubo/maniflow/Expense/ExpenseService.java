package com.truebubo.maniflow.Expense;

import java.math.BigDecimal;
import java.util.List;

/// Handles business logic behind expenses
public class ExpenseService {
    /// Gets the list of all the expenses saved by the user
    ///
    /// @return All the expenses saved
    public List<Expense> getExpenses() {
        return List.of();
    }

    /// Saves the expense
    ///
    /// @param expense Information about the expense to be added
    public void addExpense(Expense expense) {

    }

    /// Change the value on the expense with given id
    ///
    /// @param id        ID identifying the expense. It is the left value displayed when calling showExpenses
    /// @param newAmount Will update the value to this value. If set to zero. The value will be removed
    public void changeExpense(long id, BigDecimal newAmount) {

    }
}
