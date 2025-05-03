package com.truebubo.maniflow.Stats;

import com.truebubo.maniflow.Money.CurrencyDesignation;

import java.math.BigDecimal;
import java.util.Map;

/// Class to represent stats
/// @param ownsMoney Money currently owned not tied in assets
/// @param ownsStocks How much of each currency is owned
/// @param owesMoney Money owned via debts
public record Stats(Map<CurrencyDesignation, BigDecimal> ownsMoney,
                    Map<String, BigDecimal> ownsStocks,
                    Map<CurrencyDesignation, BigDecimal> owesMoney) {
}
