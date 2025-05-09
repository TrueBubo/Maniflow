package com.truebubo.maniflow.stock;

import java.util.List;
import java.util.Optional;

/// Interface used for dealing with storage and retrieval of stock
public interface StockRepository {
    /// Saves the stock to storage
    ///
    /// @param stock Stock saved
    /// @return stock saved
    Stock saveStock(Stock stock);

    /// Removes the stock with given ticket
    ///
    /// @param ticket Ticket associated with the stock
    void deleteStock(String ticket);

    /// Finds the stock in storage
    ///
    /// @param ticket Ticket associated with the stock
    /// @return Stock if found or empty if not
    Optional<Stock> getStock(String ticket);

    /// Finds all the stocks saved
    ///
    /// @return List of all the stocks saved
    List<Stock> getStocks();
}
