package com.truebubo.maniflow.Money;

import org.springframework.lang.NonNull;

import java.io.IOException;

/// Used when the currency could not be converted for whatever reason
public class ConversionFailedException extends IOException {
    /// No message exception
    public ConversionFailedException() {
        super();
    }

    /// Exception with message
    ///
    /// @param message cause of the exception
    public ConversionFailedException(@NonNull String message) {
        super(message);
    }
}
