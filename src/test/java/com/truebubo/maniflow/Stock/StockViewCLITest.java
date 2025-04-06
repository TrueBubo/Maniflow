package com.truebubo.maniflow.Stock;

import com.truebubo.maniflow.UtilTests.UtilTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StockViewCLITest {
    private StockService stockService;

    @BeforeEach
    void setUp() {
        stockService = Mockito.mock(StockService.class);
    }

    @Test
    void showOwnedStocks() {
        String output = UtilTests.capturePrintedOutput(() -> {
            var mockStocks = List.of(
                    new Stock("AAPL", BigDecimal.ONE),
                    new Stock("ABEA", BigDecimal.TEN)
            );
            when(stockService.getStockHoldings()).thenReturn(mockStocks);
            new StockViewCLI(stockService).showOwnedStocks();
        });
        assertEquals("AAPL 1\nABEA 10\n", output);
    }
}