package com.truebubo.maniflow.Stock;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
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
        var latestStockWithTicket = stockRepository.getStock(stock.ticket());
        latestStockWithTicket.ifPresent(oldStock -> stockRepository.deleteStock(oldStock.ticket()));
        var newStock = new Stock(stock.ticket(), stock.volume().add(
                latestStockWithTicket.map(Stock::volume).orElse(BigDecimal.ZERO))
        );
        stockRepository.saveStock(newStock);
    }

    /// Sells the stocks. The price will be accounted as a one-time income
    ///
    /// @param stock Information about the stock sold
    public void sellStock(@NonNull Stock stock) throws TooFewStocksOwned {
        var latestStockWithTicket = stockRepository.getStock(stock.ticket());
        if (latestStockWithTicket.isEmpty()) throw new TooFewStocksOwned("Stock was not found. Could not sell it");

        var oldVolume = latestStockWithTicket.get().volume();
        if (oldVolume.compareTo(stock.volume()) < 0) throw new TooFewStocksOwned(String.format("Stocks volume sold %s is larger than own %s. Did not sell", stock.volume().toPlainString(), oldVolume.toPlainString()));

        stockRepository.deleteStock(stock.ticket());

        stockRepository.saveStock(new Stock(stock.ticket(), oldVolume.subtract(stock.volume())));
    }
}
