package com.truebubo.maniflow.Debt;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.lang.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class MongoDebtRepository implements DebtRepository {
    private final MongoCollection<Debt> debtCollection;

    public MongoDebtRepository(@NonNull MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase("local");
        this.debtCollection = database.getCollection("debt", Debt.class);
    }

    @Override
    public Debt saveDebt(@NonNull Debt debt) {
        debtCollection.insertOne(debt);
        return debt;
    }

    @Override
    public Optional<Debt> getDebt(int id) {
        List<Debt> debts = getDebts();
        return (debts.size() <= id) ? empty() : of(debts.get(id));
    }

    @Override
    public List<Debt> getDebts() {
        return debtCollection.find().into(new ArrayList<>());
    }

    @Override
    public Optional<Debt> changeDebt(int id, @NonNull BigDecimal newAmount) {
        return getDebt(id).map(debt -> {
            Instant createdAt = debt.created();
            if (newAmount.equals(BigDecimal.ZERO)) {
                debtCollection.deleteOne(eq("created", createdAt));
                return null;
            }
            Instant now = Instant.now();
            debtCollection.updateOne(eq("created", createdAt), combine(
                    set("value", newAmount), set("created", now)
            ));
            return new Debt(newAmount, debt.currencyDesignation(), debt.yearlyInterest(), now);
        });
    }
}
