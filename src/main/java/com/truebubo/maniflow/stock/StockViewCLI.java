package com.truebubo.maniflow.stock;

import org.springframework.lang.NonNull;

import java.util.List;

import static java.util.stream.IntStream.range;

/// CLI Frontend for debt portion of the application
public class StockViewCLI {
    private final StockService stockService;

    /// Created CLI frontend for stocks
    ///
    /// @param stockService Service for stocks
    public StockViewCLI(@NonNull StockService stockService) {
        this.stockService = stockService;
    }

    /// Displays the list of all the expenses for the user
    public void showOwnedStocks() {
        List<Stock> stockHoldings = stockService.getStockHoldings();
        range(0, stockHoldings.size()).forEach(idx -> {
            final var stock = stockHoldings.get(idx);
            System.out.println(stock.ticket() + " " + stock.volume());
        });
    }

    /// Buys the stock with given info
    ///
    /// @param stock Stock bought
    /// @throws StockNotFoundException If stock was not found by API
    public void buyStock(@NonNull Stock stock) throws StockNotFoundException {
        stockService.buyStock(stock);
    }

    /// Sells the stock with given info
    ///
    /// @param stock Stock sold
    /// @throws StockNotFoundException     If stock was not found by API
    /// @throws TooFewStocksOwnedException If wanted to sell more than owned
    public void sellStock(@NonNull Stock stock) throws StockNotFoundException, TooFewStocksOwnedException {
        stockService.sellStock(stock);
    }
}
