package com.truebubo.maniflow.debt;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/// Repository used for dealing with storage and retrieval of debts using MongoDB
@Repository
public class MongoDebtRepository implements DebtRepository {
    private final MongoCollection<Debt> debtCollection;

    /// Creates mongo repository
    ///
    /// @param mongoClient Mongo client to be used
    public MongoDebtRepository(@NonNull MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase("local");
        this.debtCollection = database.getCollection("debt", Debt.class);
    }

    @Override
    public Debt saveDebt(@NonNull Debt debt) {
        debtCollection.insertOne(new Debt(debt.value().round(MathContext.DECIMAL64), debt.currencyDesignation(), debt.yearlyInterest(), debt.created()));
        return debt;
    }

    @Override
    public Optional<Debt> getDebt(int id) {
        List<Debt> debts = getDebts();
        return (id - 1 < 0 || debts.size() <= id - 1) ? empty() : of(debts.get(id - 1));
    }

    @Override
    public List<Debt> getDebts() {
        return debtCollection.find().into(new ArrayList<>());
    }

    @Override
    public Optional<Debt> changeDebt(int id, @NonNull BigDecimal newAmount) {
        return getDebt(id).map(debt -> {
            Instant createdAt = debt.created();
            if (newAmount.compareTo(BigDecimal.ZERO) <= 0) {
                debtCollection.deleteOne(eq("created", createdAt));
                return null;
            }
            Instant now = Instant.now();
            debtCollection.updateOne(eq("created", createdAt), combine(
                    set("value", newAmount.round(MathContext.DECIMAL64)), set("created", now)
            ));
            return new Debt(newAmount, debt.currencyDesignation(), debt.yearlyInterest(), now);
        });
    }
}
