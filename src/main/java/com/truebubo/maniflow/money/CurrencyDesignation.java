package com.truebubo.maniflow.money;

import org.springframework.lang.NonNull;

/// 3 letter codes of currencies
public enum CurrencyDesignation {
    /// US dollar
    USD,
    /// Euro
    EUR,
    /// British pound
    GBP,
    /// Czech crown
    CZK;

    /// Parses currency designation from a string
    ///
    /// @param designation 3 letter code of a currency
    /// @return Corresponding currency
    /// @throws UnsupportedCurrencyException If the currency designation is not found in the list of supported currencies
    public static CurrencyDesignation fromString(@NonNull String designation) throws UnsupportedCurrencyException {
        return switch (designation.toUpperCase()) {
            case "USD" -> USD;
            case "EUR" -> EUR;
            case "GBP" -> GBP;
            case "CZK" -> CZK;
            default ->
                    throw new UnsupportedCurrencyException(String.format("The system does not support currency %s", designation));
        };
    }
}
