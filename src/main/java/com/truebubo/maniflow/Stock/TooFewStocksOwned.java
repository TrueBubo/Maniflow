package com.truebubo.maniflow.Stock;

/// Exception to be throws when one wants to sell more than they own
public class TooFewStocksOwned extends Exception {
    public TooFewStocksOwned(String message) {
        super(message);
    }
}
