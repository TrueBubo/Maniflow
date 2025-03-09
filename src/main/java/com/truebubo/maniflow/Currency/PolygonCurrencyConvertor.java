package com.truebubo.maniflow.Currency;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

public class PolygonCurrencyConvertor implements CurrencyConverter {
    private String apiKey;
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
    public BigDecimal convert(CurrencyDesignation from, CurrencyDesignation to) {
        return null;
    }
}
