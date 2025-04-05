package com.truebubo.maniflow.Debt;

import java.util.List;

import static java.util.stream.IntStream.range;

/// CLI Frontend for debt portion of the application
public class DebtViewCLI {
    private final DebtService debtService;

    public DebtViewCLI(DebtService debtService) {
        this.debtService = debtService;
    }

    /// Displays the list of all the expenses for the user
    public void showDebts() {
        List<Debt> debts = debtService.getDebts();
        range(0, debts.size()).forEach(idx -> {
            final var debt = debts.get(idx);
            System.out.println(idx + 1 + ". " + debt.value() + debt.currencyDesignation() + " " + debt.yearlyInterest() + "%");
        });
    }
}
