package com.truebubo.maniflow.Income;

import com.truebubo.maniflow.Money.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/// Handles business logic behind income
public class IncomeService {
    /// Gets the list of all the incomes saved by the user
    ///
    /// @return All the incomes saved
    public List<Income> getIncomes() {
        return List.of();
    }

    /// Saves the income
    ///
    /// @param income Information about the income to be added
    public void addIncome(@NonNull Income income) {

    }

    /// Change the value on the income with given id
    ///
    /// @param id        ID identifying the income. It is the left value displayed when calling showIncomes
    /// @param newAmount Will update the value to this value. If set to zero. The value will be removed
    public void changeIncome(int id, @NonNull BigDecimal newAmount) {

    }
}
