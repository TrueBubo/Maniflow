package com.truebubo.maniflow.Debt;

import com.truebubo.maniflow.Expense.Expense;

import java.math.BigDecimal;
import java.util.List;

/// Handles business logic behind debts
public class DebtService {
    /// Gets the list of all the debts saved by the user
    ///
    /// @return All the debts saved
    public List<Debt> getDebts() {
        return List.of();
    }

    /// Saves the debt
    ///
    /// @param debt Information about the debt to be added
    public void addDebt(Debt debt) {

    }

    /// Pay the value on the debt with given id
    ///
    /// @param id     ID identifying the debt. It is the left value displayed when calling showDebts
    /// @param amount Will deduct this much from the debt
    public void changeExpense(long id, BigDecimal amount) {
    }
}