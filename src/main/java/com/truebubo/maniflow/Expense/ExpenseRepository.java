package com.truebubo.maniflow.Expense;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/// Interface used for dealing with storage and retrieval of expense
public interface ExpenseRepository {
    /// Saves the expense to storage
    ///
    /// @param expense Expense saved
    /// @return expense saved
    @Nullable
    Expense saveExpense(@NonNull Expense expense);

    /// Finds the expense with given id
    ///
    /// @param id ID as shown by showExpenses
    /// @return Expense if found else empty
    Optional<Expense> getExpense(int id);

    /// Finds all the expenses saved
    ///
    /// @return List of all the expenses saved
    @NonNull
    List<Expense> getExpenses();

    /// Changes the expense with given ID
    ///
    /// @param id        ID identifying the expense. It is the left value displayed when calling showExpenses
    /// @param newAmount Will update the value to this value. If set to zero. The expanse will be removed
    /// @return Changed expense or empty if did not change
    Optional<Expense> changeExpense(int id, @NonNull BigDecimal newAmount);
}
