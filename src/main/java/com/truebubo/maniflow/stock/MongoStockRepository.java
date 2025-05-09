package com.truebubo.maniflow.stock;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Optional.ofNullable;

/// Repository used for dealing with storage and retrieval of stock using MongoDB
public class MongoStockRepository implements StockRepository {
    private final MongoCollection<Stock> stockCollection;

    /// Creates mongo repository
    ///
    /// @param mongoClient Mongo client to be used
    public MongoStockRepository(@NonNull MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase("local");
        this.stockCollection = database.getCollection("stocks", Stock.class);
    }

    @Override
    public @NonNull Stock saveStock(@NonNull Stock stock) {
        stockCollection.insertOne(stock);
        return stock;
    }

    @Override
    public void deleteStock(String ticket) {
        stockCollection.deleteOne(eq("ticket", ticket));
    }

    @Override
    public Optional<Stock> getStock(String ticket) {
        return ofNullable(
                stockCollection.find(eq("ticket", ticket)
                ).first()
        );
    }

    @Override
    public List<Stock> getStocks() {
        return stockCollection.find().into(new ArrayList<>());
    }


}
