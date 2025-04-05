package com.truebubo.maniflow.Income;

import java.util.List;

/// CLI Frontend for income portion of the application
public class IncomeViewCLI {
    private final IncomeService incomeService;

    public IncomeViewCLI(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    /// Displays the list of all the incomes for the user
    void showIncomes() {
        List<Income> incomes = incomeService.getIncomes();
        incomes.forEach(income -> {
            System.out.println(income.value().toString() + income.currencyDesignation());
        });
    }
}
