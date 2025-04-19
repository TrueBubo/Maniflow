package com.truebubo.maniflow.Stock;

import java.util.Optional;

@FunctionalInterface
public interface StockPriceFinder {
    /// Finds the price of one unit of a stock
    ///
    /// @param ticket Ticket the stock is sold under
    Optional<StockPrice> find(String ticket);
}
