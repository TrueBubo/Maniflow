package com.truebubo.maniflow.Stock;

import org.springframework.lang.NonNull;

import java.util.List;

/// Handles business logic behind expenses
public class StockService {
    private StockRepository stockRepository;

    public StockService(@NonNull StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /// Gets the list of all the stocks owned by the user
    ///
    /// @return All the stocks currently owned by the user
    public List<Stock> getStockHoldings() {
        return stockRepository.getStocks();
    }

    /// Buys the stocks. The price will be deducted as a one-time expense
    ///
    /// @param stock Information about the stock bought
    public void buyStock(@NonNull Stock stock) {

    }

    /// Sells the stocks. The price will be accounted as a one-time income
    ///
    /// @param stock Information about the stock sold
    public void sellStock(@NonNull Stock stock) {
    }
}
