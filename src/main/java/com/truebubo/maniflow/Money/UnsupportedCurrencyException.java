package com.truebubo.maniflow.Money;

/// Currency is not yet supported
public class UnsupportedCurrencyException extends UnsupportedOperationException {
    /// Creates exception with the reason
    /// @param message Custom message what happened
    public UnsupportedCurrencyException(String message) {
        super(message);
    }
}
