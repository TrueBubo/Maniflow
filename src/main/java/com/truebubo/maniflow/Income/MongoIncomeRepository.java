package com.truebubo.maniflow.Income;

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

/// Repository used for dealing with storage and retrieval of income using MongoDB
public class MongoIncomeRepository implements IncomeRepository {
    private final MongoCollection<Income> incomeCollection;

    /// Creates mongo repository
    ///
    /// @param mongoClient Mongo client to be used
    public MongoIncomeRepository(@NonNull MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase("local");
        this.incomeCollection = database.getCollection("income", Income.class);
    }

    @Override
    public @Nullable Income saveIncome(@NonNull Income income) {
        incomeCollection.insertOne(income);
        return income;
    }

    @Override
    public @NonNull Optional<Income> getIncome(int id) {
        List<Income> incomes = getIncomes();
        return (id - 1 < 0 || incomes.size() <= id - 1) ? empty() : of(incomes.get(id - 1));
    }

    @Override
    public @NonNull List<Income> getIncomes() {
        return incomeCollection.find().into(new ArrayList<>());
    }

    @Override
    public @Nullable Optional<Income> changeIncome(int id, @NonNull BigDecimal newAmount) {
        return getIncome(id).map(income -> {
                    Instant createdAt = income.created();
                    if (newAmount.equals(BigDecimal.ZERO)) {
                        incomeCollection.deleteOne(eq("created", createdAt));
                        return null;
                    }
                    incomeCollection.updateOne(eq("created", createdAt), set("value", newAmount));
                    return new Income(newAmount, income.currencyDesignation(), createdAt, income.repeatsAfterDays());
                }
        );
    }
}
