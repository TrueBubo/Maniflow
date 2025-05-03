package com.truebubo.maniflow.Expense;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.Nullable;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/// Repository used for dealing with storage and retrieval of expense using MongoDB
public class MongoExpenseRepository implements ExpenseRepository {
    private final MongoCollection<Expense> expenseCollection;

    public MongoExpenseRepository(@NonNull MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase("local");
        this.expenseCollection = database.getCollection("expense", Expense.class);
    }

    @Override
    public @Nullable Expense saveExpense(@NonNull Expense expense) {
        expenseCollection.insertOne(expense);
        return expense;
    }

    @Override
    public @NonNull Optional<Expense> getExpense(int id) {
        List<Expense> expenses = getExpenses();
        return (id - 1 < 0 || expenses.size() <= id - 1) ? empty() : of(expenses.get(id - 1));
    }

    @Override
    public @NonNull List<Expense> getExpenses() {
        return expenseCollection.find().into(new ArrayList<>());
    }

    @Override
    public @Nullable Optional<Expense> changeExpense(int id, @NonNull BigDecimal newAmount) {
        return getExpense(id).map(expense -> {
                    Instant createdAt = expense.created();
                    if (newAmount.equals(BigDecimal.ZERO)) {
                        expenseCollection.deleteOne(eq("created", createdAt));
                        return null;
                    }
                    expenseCollection.updateOne(eq("created", createdAt), set("value", newAmount));
                    return new Expense(newAmount, expense.currencyDesignation(), createdAt, expense.repeatsAfterDays());
                }
        );
    }
}
