package com.truebubo.maniflow.stock;

/// Exception thrown if the stock is not available under the API
public class StockNotFoundException extends Exception {
    /// Creates an exception with message
    ///
    /// @param message Explanation of
    public StockNotFoundException(String message) {
        super(message);
    }
}
