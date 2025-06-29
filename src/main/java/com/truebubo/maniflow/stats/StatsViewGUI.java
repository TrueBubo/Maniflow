package com.truebubo.maniflow.stats;

import com.truebubo.maniflow.money.CurrencyDesignation;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.PostConstruct;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static com.truebubo.maniflow.ManiflowApplication.decimalRoundingDigits;

/// GUI Frontend for statistics portion of the application
@UIScope
@Component
public class StatsViewGUI extends VerticalLayout {
    private final StatsService statsService;


    public StatsViewGUI(@NonNull StatsService statsService) {
        this.statsService = statsService;
    }

    /// Sets up the statistics div with its components
    @PostConstruct
    public void init() {
        removeAll();
        final var horizontalLayout = new HorizontalLayout();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        horizontalLayout.setId("statsLayout");
        final var stats = statsService.getMoneyStats();
        final var moneyOwnedDiv = getMoneyOwnedDiv(stats.ownsMoney());
        final var stocksOwnedDiv = getStocksOwnedDiv(stats.ownsStocks());
        final var debtsOwedDiv = getDebtsOwedDiv(stats.owesMoney());

        horizontalLayout.add(
                moneyOwnedDiv,
                stocksOwnedDiv,
                debtsOwedDiv
        );
        add(horizontalLayout);
    }

    private static Div getMoneyOwnedDiv(@NonNull Map<CurrencyDesignation, BigDecimal> moneyOwned) {
        final var moneyOwnedDiv = new Div();
        moneyOwnedDiv.setId("moneyOwnedDiv");

        final var moneyOwnedTitle = new H3("Money owned");
        moneyOwnedTitle.setId("moneyOwnedTitle");

        final var moneyOwnedParagraph = new Paragraph();
        moneyOwnedParagraph.getStyle().set("white-space", "pre-line");
        moneyOwnedParagraph.setId("ownCurrencies");
        moneyOwned.forEach((currencyDesignation, value) -> {
            final var roundedValue = value.setScale(decimalRoundingDigits, RoundingMode.HALF_DOWN);
            moneyOwnedParagraph.add(
                    new Text(currencyDesignation + ": " + roundedValue + "\n")
            );
        });

        moneyOwnedDiv.add(
                moneyOwnedTitle,
                moneyOwnedParagraph
        );

        return moneyOwnedDiv;
    }

    private static Div getStocksOwnedDiv(@NonNull Map<String, BigDecimal> stocks) {
        final var stocksOwnedDiv = new Div();
        stocksOwnedDiv.setId("stocksOwnedDiv");

        final var stocksOwnedTitle = new H3("Stocks owned");
        stocksOwnedTitle.setId("stocksOwnedTitle");

        final var stocksOwnedParagraph = new Paragraph();
        stocksOwnedParagraph.getStyle().set("white-space", "pre-line");
        stocksOwnedParagraph.setId("ownStocks");
        stocks.forEach((ticket, value) -> {
            final var roundedValue = value.setScale(decimalRoundingDigits, RoundingMode.HALF_DOWN);
            stocksOwnedParagraph.add(
                    new Text(ticket + ": " + roundedValue + "\n")
            );
        });

        stocksOwnedDiv.add(
                stocksOwnedTitle,
                stocksOwnedParagraph
        );

        return stocksOwnedDiv;
    }

    private static Div getDebtsOwedDiv(@NonNull Map<CurrencyDesignation, BigDecimal> debts) {
        final var debtsOwedDiv = new Div();
        debtsOwedDiv.setId("debtsOwedDiv");

        final var debtsOwedTitle = new H3("Current debts");
        debtsOwedTitle.setId("debtsOwedTitle");

        final var debtsOwedParagraph = new Paragraph();
        debtsOwedParagraph.getStyle().set("white-space", "pre-line");
        debtsOwedParagraph.setId("debtsOwed");
        debts.forEach((currencyDesignation, value) -> {
            final var roundedValue = value.setScale(decimalRoundingDigits, RoundingMode.HALF_DOWN);
            debtsOwedParagraph.add(
                    new Text(currencyDesignation + ": " + roundedValue + "\n")
            );
        });

        debtsOwedDiv.add(
                debtsOwedTitle,
                debtsOwedParagraph
        );

        return debtsOwedDiv;
    }
}
