package com.truebubo.maniflow.Money;

import java.util.Optional;

/// Converts currencies between themselve
@FunctionalInterface
public interface CurrencyConverter {
    /// How much of the {to currency} can we buy for 1 {from currency}
    ///
    /// @param from Currency we sell
    /// @param to   Currency we buy
    /// @return exchange rate
    Optional<Double> convert(CurrencyDesignation from, CurrencyDesignation to);
}
