package com.truebubo.maniflow.Stock;

/// Exception thrown if the stock is not available under the API
public class StockNotFoundException extends Exception {
    /// Creates an exception with message
    /// @param message Explanation of an exception
    public StockNotFoundException(String message) {super(message);}
}
