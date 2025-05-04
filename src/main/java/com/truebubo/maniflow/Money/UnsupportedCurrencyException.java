package com.truebubo.maniflow.Money;

import org.springframework.lang.NonNull;

/// Currency is not yet supported
public class UnsupportedCurrencyException extends UnsupportedOperationException {
    /// Creates exception with the reason
    ///
    /// @param message Custom message what happened
    public UnsupportedCurrencyException(@NonNull String message) {
        super(message);
    }
}
