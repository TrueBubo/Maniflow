package com.truebubo.maniflow.Income;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.IntStream.range;

/// CLI Frontend for income portion of the application
public class IncomeViewCLI {
    private final IncomeService incomeService;

    /// CLI front-end for incomes
    ///
    /// @param incomeService service for accessing income back-end
    public IncomeViewCLI(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    /// Displays the list of all the incomes for the user
    public void showIncomes() {
        List<Income> incomes = incomeService.getIncomes();
        range(0, incomes.size()).forEach(idx -> {
            final var income = incomes.get(idx);
            System.out.println(idx + 1 + ". " + income.value() + income.currencyDesignation());
        });
    }

    /// Saves the income
    ///
    /// @param income Information about the income to be added
    public void addIncome(Income income) {
        incomeService.addIncome(income);
    }

    /// Change the value on the income with given id
    ///
    /// @param id        ID identifying the income. It is the left value displayed when calling showIncomes
    /// @param newAmount Will update the value to this value. If set to zero. The value will be removed
    public void changeIncome(int id, @NonNull BigDecimal newAmount) {
        incomeService.changeIncome(id, newAmount);
    }
}
