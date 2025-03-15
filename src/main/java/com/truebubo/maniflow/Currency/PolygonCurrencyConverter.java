package com.truebubo.maniflow.Currency;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import static java.util.Optional.of;
import static java.util.Optional.empty;

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

    private PolygonCurrencyConvertor instance;
    private PolygonCurrencyConvertor() {
        final Properties properties = new Properties();
        final String apiFile = "api.properties";
        try (var resourceStream = new FileInputStream(apiFile)){
            properties.load(resourceStream);
            this.apiKey = properties.getProperty("POLYGON_API_KEY");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public PolygonCurrencyConvertor get() {
        return instance == null ? new PolygonCurrencyConvertor() : instance;
    }

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
        } catch (IOException | InterruptedException e) {
            return empty();
        }
    }
}
