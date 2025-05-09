package com.truebubo.maniflow.stats;

import com.truebubo.maniflow.money.CurrencyDesignation;
import com.truebubo.maniflow.util_tests.UtilTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StatsViewCLITest {
    private StatsService statsService;

    @BeforeEach
    void setUp() {
        statsService = Mockito.mock(StatsService.class);
    }

    @Test
    void showMoneyStats() {
        String output = UtilTests.capturePrintedOutput(() -> {
            when(statsService.getMoneyStats()).thenReturn(new Stats(
                    Map.of(CurrencyDesignation.CZK, BigDecimal.TWO,
                            CurrencyDesignation.EUR, BigDecimal.TEN),
                    Map.of("AAPL", BigDecimal.ONE,
                            "GOOG", BigDecimal.valueOf(3)),
                    Map.of(CurrencyDesignation.GBP, BigDecimal.ONE,
                            CurrencyDesignation.USD, BigDecimal.TWO)
                    ));
            new StatsViewCLI(statsService).showStats();
        });
        assertTrue(output.contains("CZK: 2"));
        assertTrue(output.contains("EUR: 10"));
        assertTrue(output.contains("AAPL: 1"));
        assertTrue(output.contains("GOOG: 3"));
        assertTrue(output.contains("GBP: 1"));
        assertTrue(output.contains("USD: 2"));

    }
}