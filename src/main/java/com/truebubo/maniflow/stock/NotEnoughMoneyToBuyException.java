package com.truebubo.maniflow.stock;

/// Exception thrown if cannot afford to buy stocks
public class NotEnoughMoneyToBuyException extends Exception {
    /// Creates an exception with message
    ///
    /// @param message Explanation of
    public NotEnoughMoneyToBuyException(String message) {
        super(message);
    }
}
