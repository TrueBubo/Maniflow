package com.truebubo.maniflow.stock;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.truebubo.maniflow.ManiflowApplication.decimalRoundingDigits;
import static com.truebubo.maniflow.commongui.CommonGUI.*;
import static com.truebubo.maniflow.commongui.CommonGUI.getFormLayout;
import static com.truebubo.maniflow.commongui.CommonGUI.setUpFormFieldListeners;

/// GUI Frontend for stock portion of the application
@UIScope
@Component
public class StockViewGUI extends VerticalLayout {
    private final StockService stockService;
    public StockViewGUI(StockService stockService) {
        this.stockService = stockService;
    }

    /// Sets up the expense div with its components
    @PostConstruct
    public void init() {
        removeAll();
        final var verticalLayout = new VerticalLayout();
        verticalLayout.setId("stockLayout");

        final var addDebtTitle = new H2("Buy stocks");
        addDebtTitle.setId("buyStockTitle");

        final var expensesTitle = new H2("Stocks");
        expensesTitle.setId("stocksTitle");

        verticalLayout.add(addDebtTitle, getStockFormLayout(), expensesTitle, getStocksDiv(stockService.getStockHoldings()));
        add(verticalLayout);
    }

    private FormLayout getStockFormLayout() {
        final var valueField = getValueField("Stock", value -> value != null && value.compareTo(BigDecimal.ZERO) < 0);
        valueField.setRequired(true);
        final var ticketField = getTextField("Stock", "Ticket");
        ticketField.setRequired(true);

        final var submitButton = getSubmitButton("Stock");
        addSubmitHandler(submitButton, stockService, valueField, ticketField);
        setUpFormFieldListeners(submitButton, valueField, ticketField);
        return getFormLayout(submitButton, valueField, ticketField);
    }

    private void addSubmitHandler(Button submitButton, StockService stockService,
                                  BigDecimalField valueField,
                                  TextField ticketField) {
        submitButton.addClickListener(_ -> {
            try {
                stockService.buyStock(
                        new Stock(ticketField.getValue(), valueField.getValue())
                );
            } catch (StockNotFoundException e) {
                Notification.show("Stock could not be found");
                return;
            } catch (NotEnoughMoneyToBuyException e) {
                Notification.show(e.getMessage());
                return;
            }
            init();
            Notification.show("New stock bought successfully!");
        });
    }

    private Div getStocksDiv(List<Stock> stocks) {
        final var stocksDiv = new Div();
        stocksDiv.getStyle().set("white-space", "pre-line");
        stocksDiv.setId("stockList");

        stocks.reversed().forEach(stock -> {
            Div row = new Div();
            row.getStyle()
                    .set("display", "flex")
                    .set("justify-content", "space-between");

            final var roundedVolume = stock.volume().setScale(decimalRoundingDigits, RoundingMode.HALF_DOWN);
            final var text = new Span(stock.ticket() + ": " + roundedVolume + "\n");
            final var sellLabel = new Span("Sell");
            sellLabel.getStyle().set("color", "gray").setPaddingLeft("3em").setPaddingRight("0.5em");
            final var sellField = new NumberField();
            sellField.setMax(stock.volume().doubleValue());
            sellField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            sellField.getStyle().setPadding("0 0");

            sellField.addKeyPressListener(Key.ENTER, _ -> {
                final var soldVolume = sellField.getValue();
                try {
                    stockService.sellStock(new Stock(stock.ticket(), BigDecimal.valueOf(soldVolume)));
                } catch (TooFewStocksOwnedException e) {
                    Notification.show("Cannot sell more stock than owned");
                } catch (StockNotFoundException e) {
                    Notification.show("Such stock does not exist");
                }
                init();
            });

            row.add(text, sellLabel, sellField);

            stocksDiv.add(row);
        });
        return stocksDiv;
    }
}
