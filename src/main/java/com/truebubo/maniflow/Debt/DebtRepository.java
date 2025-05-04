package com.truebubo.maniflow.Debt;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/// Interface used for dealing with storage and retrieval of debt
public interface DebtRepository {
    /// Saves the debt to storage
    ///
    /// @param debt Debt saved
    /// @return debt saved
    Debt saveDebt(@NonNull Debt debt);

    /// Finds the debt with given id
    ///
    /// @param id ID as shown in showDebts
    /// @return Debt if found otherwise empty
    Optional<Debt> getDebt(int id);

    /// Finds all the current debt
    ///
    /// @return List of all the debts saved
    List<Debt> getDebts();

    /// Changes the debt with given id
    ///
    /// @param id        ID identifying the debt. It is the left value displayed when calling showExpenses
    /// @param newAmount Will update the value to this value. If set to zero. The debt will be erased
    /// @return Value of new debt or empty if not exist
    Optional<Debt> changeDebt(int id, @NonNull BigDecimal newAmount);
}
