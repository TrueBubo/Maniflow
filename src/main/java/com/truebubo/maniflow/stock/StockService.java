package com.truebubo.maniflow.stock;

import com.truebubo.maniflow.expense.Expense;
import com.truebubo.maniflow.expense.ExpenseRepository;
import com.truebubo.maniflow.expense.ExpenseService;
import com.truebubo.maniflow.income.Income;
import com.truebubo.maniflow.income.IncomeRepository;
import com.truebubo.maniflow.income.IncomeService;
import com.truebubo.maniflow.stats.StatsService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/// Handles business logic behind stocks
@Service
public class StockService {
    private final StockRepository stockRepository;
    private final StockPriceFinder stockPriceFinder;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    /// Initializes stock service with repositories
    ///
    /// @param stockRepository   Stock repository
    /// @param stockPriceFinder  Finds stock prices
    /// @param incomeRepository  Income repository
    /// @param expenseRepository Expense repository
    public StockService(@NonNull StockRepository stockRepository, @NonNull StockPriceFinder stockPriceFinder,
                        @NonNull IncomeRepository incomeRepository, @NonNull ExpenseRepository expenseRepository) {
        this.stockRepository = stockRepository;
        this.stockPriceFinder = stockPriceFinder;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
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
    /// @throws StockNotFoundException Stock was not found by an API
    /// @throws NotEnoughMoneyToBuyException The price of stocks to be bought is higher than money the user owns
    public void buyStock(@NonNull Stock stock) throws StockNotFoundException, NotEnoughMoneyToBuyException {
        var stockPrice = stockPriceFinder.find(stock.ticket());
        if (stockPrice.isEmpty()) {
            throw new StockNotFoundException("Could not find stock with ticket: " + stock.ticket());
        }
        StockPrice price = stockPrice.get();
        BigDecimal toPay = price.value().multiply(stock.volume());
        BigDecimal ownsWantedCurrency = StatsService.getOwnsMoneyPerCurrency(new IncomeService(incomeRepository), new ExpenseService(expenseRepository))
                .getOrDefault(price.currency(), BigDecimal.ZERO);
        if (toPay.compareTo(ownsWantedCurrency) > 0) {
            throw new NotEnoughMoneyToBuyException("Not enough money to buy stock");
        }

        expenseRepository.saveExpense(new Expense(toPay, price.currency(), Instant.now()));
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
    /// @throws TooFewStocksOwnedException When quantity sold is larger than owned
    /// @throws StockNotFoundException     Stock was not found by an API
    public void sellStock(@NonNull Stock stock) throws TooFewStocksOwnedException, StockNotFoundException {
        var stockPrice = stockPriceFinder.find(stock.ticket());
        if (stockPrice.isEmpty())
            throw new StockNotFoundException("Could not find stock with ticket: " + stock.ticket());
        StockPrice price = stockPrice.get();
        incomeRepository.saveIncome(new Income(price.value().multiply(stock.volume()), price.currency(), Instant.now()));

        var latestStockWithTicket = stockRepository.getStock(stock.ticket());
        if (latestStockWithTicket.isEmpty())
            throw new TooFewStocksOwnedException("Stock was not found. Could not sell it");

        var oldVolume = latestStockWithTicket.get().volume();
        if (oldVolume.compareTo(stock.volume()) < 0)
            throw new TooFewStocksOwnedException(
                    String.format("Stocks volume sold %s is larger than own %s. Did not sell",
                            stock.volume().toPlainString(), oldVolume.toPlainString()));

        stockRepository.deleteStock(stock.ticket());

        stockRepository.saveStock(new Stock(stock.ticket(), oldVolume.subtract(stock.volume())));
    }
}
