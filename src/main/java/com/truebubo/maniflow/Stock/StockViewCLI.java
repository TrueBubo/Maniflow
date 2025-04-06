package com.truebubo.maniflow.Stock;

import com.truebubo.maniflow.Debt.Debt;

import java.util.List;

import static java.util.stream.IntStream.range;

/// CLI Frontend for debt portion of the application
public class StockViewCLI {
    private final StockService stockService;

    public StockViewCLI(StockService stockService) {
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
}
