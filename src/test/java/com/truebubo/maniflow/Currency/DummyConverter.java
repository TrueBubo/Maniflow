package com.truebubo.maniflow.Currency;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DummyConvertor implements CurrencyConverter {
    private Map<CurrencyDesignation, Map<CurrencyDesignation, Double>> converter;
    private DummyConvertor instance;

    private DummyConvertor() {
        Map<CurrencyDesignation, Map<CurrencyDesignation, Double>> map = new HashMap<>();
        Map<CurrencyDesignation, Double> usdTo = new HashMap<>();
        usdTo.put(CurrencyDesignation.USD, 1.0);
        usdTo.put(CurrencyDesignation.EUR, 0.9);
        usdTo.put(CurrencyDesignation.CZK, 23d);
        Map<CurrencyDesignation, Double> euroTo = new HashMap<>();
        euroTo.put(CurrencyDesignation.EUR, 1.0);
        euroTo.put(CurrencyDesignation.CZK, 25d);
        euroTo.put(CurrencyDesignation.USD, 1.1);
    }

    /**
     * How much of the {to currency} can we buy for 1 {from currency}
     * @param from Currency we sell
     * @param to   Currency we buy
     * @return exchange rate
     */
    @Override
    public Optional<Double> convert(CurrencyDesignation from, CurrencyDesignation to) {
        try {
            return Optional.ofNullable(converter.get(from).get(to));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    /**
     * Gets an instance of a convertor
     *
     * @return instance
     */
    @Override
    public CurrencyConverter get() {
        return instance == null ? new DummyConvertor() : instance;

    }
}
