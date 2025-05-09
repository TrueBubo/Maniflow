package com.truebubo.maniflow.debt;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.IntStream.range;

/// CLI Frontend for debt portion of the application
public class DebtViewCLI {
    private final DebtService debtService;

    /// CLI front-end for debts
    ///
    /// @param debtService service for accessing debt back-end
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

    /// Saves the debt
    ///
    /// @param debt Information about the debt to be added
    public void addDebt(Debt debt) {
        debtService.addDebt(debt);
    }

    /// Pay the value on the debt with given id
    ///
    /// @param id     ID identifying the debt. It is the left value displayed when calling showDebts
    /// @param amount Will deduct this much from the debt
    public void payDebt(int id, BigDecimal amount) {
        debtService.payDebt(id, amount);
    }
}
