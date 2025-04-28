package com.truebubo.maniflow.Income;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/// Interface used for dealing with storage and retrieval of income
public interface IncomeRepository {
    /// Saves the income to storage
    ///
    /// @param income Income saved
    /// @return income saved
    Income saveIncome(@NonNull Income income);

    /// Finds the income with given id
    Optional<Income> getIncome(int id);

    /// Finds all the incomes saved
    ///
    /// @return List of all the incomes saved
    List<Income> getIncomes();

    /// Changes the income with given ID
    ///
    /// @param id        ID identifying the income. It is the left value displayed when calling showIncomes
    /// @param newAmount Will update the value to this value. If set to zero. The value will be removed
    Optional<Income> changeIncome(int id, @NonNull BigDecimal newAmount);
}
