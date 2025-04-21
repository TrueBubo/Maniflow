package com.truebubo.maniflow.Stock;

import java.util.Optional;

/// Interface used for dealing with storage and retrieval of stock
public interface StockRepository {
    /// Saves the stock to storage
    /// @param stock Stock saved
    /// @return stock saved
    Stock saveStock(Stock stock);

    /// Finds the stock in the story
    /// @param ticket Ticket associated with the stock
    /// @return Stock if found or empty if not
    Optional<Stock> getStock(String ticket);
}
