package com.truebubo.maniflow.Currency;

public enum CurrencyDesignation {
    USD,
    EUR,

    GBP,
    CZK;

    public static CurrencyDesignation fromString(String designation) throws UnsupportedCurrencyException {
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
