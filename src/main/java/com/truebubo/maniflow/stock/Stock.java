package com.truebubo.maniflow.stock;

import java.math.BigDecimal;

/// Information about stock
///
/// @param ticket Ticket the stock is sold under in stock exchanges
/// @param volume How many stocks are owned
public record Stock(String ticket, BigDecimal volume) {
}
