package com.truebubo.maniflow.debt;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/// Handles business logic behind debts
@Service
public class DebtService {
    DebtRepository debtRepository;

    /// Service for debts
    ///
    /// @param debtRepository Repository for data about debts
    public DebtService(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    /// Gets the list of all the debts saved by the user
    ///
    /// @return All the debts saved
    public List<Debt> getDebts() {
        return debtRepository.getDebts();
    }

    /// Saves the debt
    ///
    /// @param debt Information about the debt to be added
    public void addDebt(Debt debt) {
        debtRepository.saveDebt(debt);
    }

    /// Pay the value on the debt with given id
    ///
    /// @param id     ID identifying the debt. It is the left value displayed when calling showDebts
    /// @param amount Will deduct this much from the debt
    public void payDebt(int id, BigDecimal amount) {
        var oldDebt = debtRepository.getDebt(id);
        oldDebt.ifPresentOrElse(
                debt -> debtRepository.changeDebt(id, debt.getValueWithInterest().subtract(amount)),
                () -> System.out.println("Debt with given id not found")
        );
    }
}