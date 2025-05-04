package com.truebubo.maniflow.Stats;

import com.truebubo.maniflow.Money.CurrencyDesignation;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/// CLI Frontend for expense portion of the application
public class StatsViewCLI {
    private final StatsService statsService;
    /// ClI front-end for stats
    /// @param statsService Stats back-end
    public StatsViewCLI(@NonNull StatsService statsService) {
        this.statsService = statsService;
    }

    private static void showMoneyOwned(@NonNull Map<CurrencyDesignation, BigDecimal> moneyOwned) {
        System.out.println("Money owned:");
        moneyOwned.forEach((currencyDesignation, value) ->
                System.out.println("\t" + currencyDesignation + ": " + value));
    }

    private static void showStocks(@NonNull Map<String, BigDecimal> stocks) {
        System.out.println("Stocks:");
        stocks.forEach((ticket, amount) ->
                System.out.println("\t" + ticket + ": " + amount));
    }

    private static void showDebts(@NonNull Map<CurrencyDesignation, BigDecimal> debts) {
        System.out.println("Debts:");
        debts.forEach((currencyDesignation, value) ->
                System.out.println("\t" + currencyDesignation + ": " + value));
    }

    /// Display summary of our finances
    public void showStats() {
        Stats stats = statsService.getMoneyStats();

        showMoneyOwned(stats.ownsMoney());
        showStocks(stats.ownsStocks());
        showDebts(stats.owesMoney());



    }
}
