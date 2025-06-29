package com.truebubo.maniflow.expense;

import com.truebubo.maniflow.money.MoneyExchange;
import com.truebubo.maniflow.money.MoneyExchangeService;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/// Handles business logic behind expenses
@Service
public class ExpenseService implements MoneyExchangeService<Expense> {
    private final ExpenseRepository expenseRepository;

    /// Service for expenses
    ///
    /// @param expenseRepository Repository for data about expenses
    public ExpenseService(@NonNull ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /// Gets the list of all the expenses saved by the user
    ///
    /// @return All the expenses saved
    @Override
    public List<Expense> get() {
        return expenseRepository.getExpenses();
    }

    /// Saves the expense
    ///
    /// @param expense Information about the expense to be added
    @Override
    public void add(@NonNull Expense expense) {
        expenseRepository.saveExpense(expense);
    }

    /// Change the value on the expense with given id
    ///
    /// @param id        ID identifying the expense. It is the left value displayed when calling showExpenses
    /// @param newAmount Will update the value to this value. If set to zero. The value will be removed
    @Override
    public void change(int id, @NonNull BigDecimal newAmount) {
        expenseRepository.changeExpense(id, newAmount);
    }
}
