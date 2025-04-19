package com.truebubo.maniflow.Stock;

import com.truebubo.maniflow.Money.CurrencyDesignation;

import java.math.BigDecimal;

/// Info about the cost of a stock
/// @param value How many times larger is the price compared to the basic unit of the currency
/// @param currency Currency the price is in
public record StockPrice(BigDecimal value, CurrencyDesignation currency) {
}
