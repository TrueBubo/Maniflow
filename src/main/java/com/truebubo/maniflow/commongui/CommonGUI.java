package com.truebubo.maniflow.commongui;

import com.truebubo.maniflow.money.CurrencyDesignation;
import com.truebubo.maniflow.money.MoneyExchange;
import com.truebubo.maniflow.money.MoneyExchangeService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static com.truebubo.maniflow.ManiflowApplication.decimalRoundingDigits;
import static com.truebubo.maniflow.ManiflowApplication.timeFormatter;

/// Common UI components used throughout the application
public class CommonGUI {
    public static FormLayout getFormLayout(Button submitButton, Component... components) {
        FormLayout formLayout = new FormLayout();
        formLayout.setAutoResponsive(true);
        formLayout.setColumnWidth("10em");
        formLayout.setExpandColumns(true);

        formLayout.addFormRow(components);
        formLayout.addFormRow(submitButton);
        return formLayout;
    }

    public static BigDecimalField getValueField(String typeOfExchange, String label, Predicate<BigDecimal> validator) {
        final var valueField = new BigDecimalField(label);
        valueField.setId(typeOfExchange.toLowerCase() + "ValueField");
        valueField.setRequired(true);

        valueField.addValueChangeListener(_ -> {
            final var value = valueField.getValue();
            if (validator.test(value)) valueField.setValue(null);
        });

        return valueField;
    }

    public static BigDecimalField getValueField(String typeOfExchange, Predicate<BigDecimal> validator) {
        return getValueField(typeOfExchange, "Value", validator);
    }


    public static TextField getTextField(String typeOfExchange, String label) {
        final var textField = new TextField(label);
        textField.setId(typeOfExchange.toLowerCase() + "TextField");
        textField.setRequired(true);
        return textField;
    }

    public static ComboBox<CurrencyDesignation> getCurrencyBox(String typeOfExchange) {
        final var currencyBox = new ComboBox<CurrencyDesignation>("Currency");
        currencyBox.setItems(CurrencyDesignation.values());
        currencyBox.setId(typeOfExchange.toLowerCase() + "CurrencyDesignation");
        currencyBox.setRequired(true);
        return currencyBox;
    }

    public static IntegerField getRepeatsAfterDaysField(String typeOfExchange) {
        final var repeatsAfterDaysField = new IntegerField("Will repeat in (days)");
        repeatsAfterDaysField.setId(typeOfExchange.toLowerCase() + "RepeatsAfterDays");
        repeatsAfterDaysField.setMin(0);
        repeatsAfterDaysField.setRequired(false);
        return repeatsAfterDaysField;
    }

    public static Button getSubmitButton(String typeOfExchange) {
        final var submitButton = new Button("Submit");
        submitButton.getStyle().set("cursor", "pointer");
        submitButton.setId(typeOfExchange.toLowerCase() + "SubmitButton");
        submitButton.setEnabled(false);
        return submitButton;
    }

    public static void setUpFormFieldListeners(Button submitButton,
                                               AbstractSinglePropertyField<?, ?>... fields
    ) {
        Runnable validateFields = () -> {
            boolean isValid = Arrays.stream(fields).map(AbstractField::getValue).allMatch(value -> value != null && value != "");
            submitButton.setEnabled(isValid);
        };
        Arrays.stream(fields).forEach(field ->
                field.addValueChangeListener(_ -> validateFields.run()));
    }

    public static Icon getTrash(ComponentEventListener<ClickEvent<Icon>> onClick) {
        final var trash = VaadinIcon.TRASH.create();
        trash.getStyle().set("cursor", "pointer");
        trash.addClickListener(onClick);
        return trash;
    }

    public static <T extends MoneyExchange> Div getMoneyExchangeRow(
            T exchange,
            ComponentEventListener<ClickEvent<Icon>> onDelete) {
        final var roundedValue = exchange.value().setScale(decimalRoundingDigits, RoundingMode.HALF_DOWN);
        final var time = timeFormatter.format(exchange.created().atZone(ZoneId.systemDefault()));
        final var repeatSuffix = (exchange.repeatsAfterDays() != null) ? " every " +
                ((exchange.repeatsAfterDays() == 1) ? "day" : exchange.repeatsAfterDays() + " days") :
                "";


        Div row = new Div();
        row.getStyle()
                .set("display", "flex")
                .set("justify-content", "flex-start");

        final var text = new Span(time + " - " + roundedValue + exchange.currencyDesignation() + repeatSuffix);
        final var trash = getTrash(onDelete);
        row.add(trash, text);

        return row;
    }

    public static <T extends MoneyExchange> Div getExchangesDiv(String prefixOperation, List<T> exchanges, MoneyExchangeService<T> service, Runnable refresher) {
        final var exchangesDiv = new Div();
        exchangesDiv.getStyle().set("white-space", "pre-line");
        exchangesDiv.setId(prefixOperation.toLowerCase() + "List");

        exchanges.reversed().forEach(expense -> {
            final var row = getMoneyExchangeRow(expense, _ -> {
                service.change(exchanges.indexOf(expense) + 1, BigDecimal.ZERO);
                refresher.run();
            });
            exchangesDiv.add(row);
        });

        return exchangesDiv;
    }
}
