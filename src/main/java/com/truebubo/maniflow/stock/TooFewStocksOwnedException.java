package com.truebubo.maniflow.stock;

/// Exception to be throws when one wants to sell more than they own
public class TooFewStocksOwnedException extends Exception {
    /// Exception with message
    ///
    /// @param message Cause of the exception
    public TooFewStocksOwnedException(String message) {
        super(message);
    }
}
