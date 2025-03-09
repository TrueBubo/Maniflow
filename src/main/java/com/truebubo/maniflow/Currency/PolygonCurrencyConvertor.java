package com.truebubo.maniflow.Currency;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PolygonCurrencyConvertor implements CurrencyConverter {
    private String apiKey;
    private final Map<CurrencyDesignation, Map<CurrencyDesignation, CachedExchangeRate>> cache = new HashMap<>();
    private record ExchangeResult(
        String T, /* Exchange symbol */
        long v, /* Volume */
        double vw, /* Volume weighted price */
        double o, /* Opening price */
        double c, /* Closing price */
        double h, /* Highest price */
        double l, /* Lowest price */
        long t, /* The Unix Msec timestamp for the start of the aggregate window. */
        long n /* Number of transactions */
    ) {}
    private record ExchangeJSON(String ticker, int queryCount, int resultsCount, boolean adjusted, List<ExchangeResult> results, String status, String request_id, int count) {}
    public PolygonCurrencyConvertor() {
        final Properties properties = new Properties();
        final String apiFile = "api.properties";
        try (var resourceStream = new FileInputStream(apiFile)){
            properties.load(resourceStream);
            this.apiKey = properties.getProperty("POLYGON_API_KEY");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @Override
    public Double convert(CurrencyDesignation from, CurrencyDesignation to) {
        var mapper = new ObjectMapper();
        try {
            ExchangeJSON exchangeJSON = mapper.readValue(new File("exchange.json"), ExchangeJSON.class);
            return exchangeJSON.results.get(0).vw; // The volume weighted average price.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
