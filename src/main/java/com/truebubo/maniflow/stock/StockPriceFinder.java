package com.truebubo.maniflow.stock;

import java.util.Optional;

/// Finds the price of stocks based on ticket
@FunctionalInterface
public interface StockPriceFinder {
    /// Finds the price of one unit of a stock
    ///
    /// @param ticket Ticket the stock is sold under
    /// @return price of stock if found else empty
    Optional<StockPrice> find(String ticket);
}
