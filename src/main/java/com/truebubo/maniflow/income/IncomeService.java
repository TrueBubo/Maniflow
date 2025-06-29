package com.truebubo.maniflow.income;

import com.truebubo.maniflow.money.MoneyExchangeService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/// Handles business logic behind income
@Service
public class IncomeService implements MoneyExchangeService<Income> {
    private final IncomeRepository incomeRepository;

    /// Service for incomes
    ///
    /// @param incomeRepository Repository for data about incomes
    public IncomeService(@NonNull IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    /// Gets the list of all the incomes saved by the user
    ///
    /// @return All the incomes saved
    public List<Income> get() {
        return incomeRepository.getIncomes();
    }

    /// Saves the income
    ///
    /// @param income Information about the income to be added
    public void add(@NonNull Income income) {
        incomeRepository.saveIncome(income);
    }

    /// Change the value on the income with given id
    ///
    /// @param id        ID identifying the income. It is the left value displayed when calling showIncomes
    /// @param newAmount Will update the value to this value. If set to zero. The value will be removed
    public void change(int id, @NonNull BigDecimal newAmount) {
        incomeRepository.changeIncome(id, newAmount);
    }
}
