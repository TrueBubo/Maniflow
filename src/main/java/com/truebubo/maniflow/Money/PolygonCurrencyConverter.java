package com.truebubo.maniflow.Money;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import static java.util.Optional.of;
import static java.util.Optional.empty;

/// Converts between currencies based on polygon api
public class PolygonCurrencyConverter implements CurrencyConverter {
    private String apiKey;
    private final Map<CurrencyDesignation, Map<CurrencyDesignation, CachedExchangeRate>> cache = new HashMap<>();

    /// Schema of result from polygon API
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

    private static PolygonCurrencyConverter instance;
    private PolygonCurrencyConverter() {
        final Properties properties = new Properties();
        final String apiFile = "api.properties";
        try (var resourceStream = new FileInputStream(apiFile)){
            properties.load(resourceStream);
            this.apiKey = properties.getProperty("POLYGON_API_KEY");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /// Lazily gets the converter
    /// @return Currency converter
    public static PolygonCurrencyConverter get() {
        if (instance == null) { instance = new PolygonCurrencyConverter(); }
        return instance;
    }

    /// How much of the {to currency} can we buy for 1 {from currency}
    /// @param from Currency we sell
    /// @param to Currency we buy
    /// @return exchange rate
    @Override
    public Optional<Double> convert(CurrencyDesignation from, CurrencyDesignation to) {
        var mapper = new ObjectMapper();
        try (final var client = HttpClient.newHttpClient()) {
            final var endpoint = String.format("https://api.polygon.io/v2/aggs/ticker/C:%s%s/prev?apiKey=%s", from, to, apiKey);
            final var request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .build();
            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ExchangeJSON exchangeJSON = mapper.readValue(response.body(), ExchangeJSON.class);
            return of(exchangeJSON.results.getFirst().vw); // The volume weighted average price.
        } catch (Exception _) {
            return empty();
        }
    }
}
