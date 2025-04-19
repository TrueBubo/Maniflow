package com.truebubo.maniflow.Stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.truebubo.maniflow.Money.CurrencyDesignation;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class PolygonStockPriceFinder implements StockPriceFinder {
    private String apiKey;
    private static PolygonStockPriceFinder instance;

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

    private PolygonStockPriceFinder() {
        final Properties properties = new Properties();
        final String apiFile = "api.properties";
        try (var resourceStream = new FileInputStream(apiFile)){
            properties.load(resourceStream);
            this.apiKey = properties.getProperty("POLYGON_API_KEY");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static PolygonStockPriceFinder get() {
        if (instance == null) { instance = new PolygonStockPriceFinder(); }
        return instance;
    }

    /// Finds the price of one unit of a stock
    ///
    /// @param ticket Ticket the stock is sold under
    @Override
    public Optional<StockPrice> find(String ticket) {
        final var endpoint = String.format("https://api.polygon.io/v2/aggs/ticker/%s/prev?apiKey=%s", ticket, this.apiKey);
        var mapper = new ObjectMapper();
        try (final var client = HttpClient.newHttpClient()) {
            final var request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .build();
            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ExchangeJSON exchangeJSON = mapper.readValue(response.body(), ExchangeJSON.class);
            return of(new StockPrice(BigDecimal.valueOf(exchangeJSON.results.getFirst().c), CurrencyDesignation.USD));
        } catch (Exception _) {
            return empty();
        }
    }
}
