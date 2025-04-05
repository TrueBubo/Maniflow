package com.truebubo.maniflow.Income;

import java.util.List;

import static java.util.stream.IntStream.range;

/// CLI Frontend for income portion of the application
public class IncomeViewCLI {
    private final IncomeService incomeService;

    public IncomeViewCLI(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    /// Displays the list of all the incomes for the user
    void showIncomes() {
        List<Income> incomes = incomeService.getIncomes();
        range(0, incomes.size()).forEach(idx -> {
            final var income = incomes.get(idx);
            System.out.println(idx + 1 + ". " + income.value() + income.currencyDesignation());
        });
    }
}
