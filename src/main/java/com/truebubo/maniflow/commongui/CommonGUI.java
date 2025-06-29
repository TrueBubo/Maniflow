package com.truebubo.maniflow.commongui;

import com.truebubo.maniflow.money.CurrencyDesignation;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;

import java.math.BigDecimal;
import java.util.function.Predicate;

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

    public static BigDecimalField getValueField(String typeOfExchange, Predicate<BigDecimal> validator) {
        final var valueField = new BigDecimalField("Value");
        valueField.setId(typeOfExchange.toLowerCase() + "ValueField");
        valueField.setRequired(true);

        valueField.addValueChangeListener(_ -> {
            final var value = valueField.getValue();
            if (validator.test(value)) valueField.setValue(null);
        });

        return valueField;
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
        submitButton.setId(typeOfExchange.toLowerCase() + "SubmitButton");
        submitButton.setEnabled(false);
        return submitButton;
    }

    public static void setUpFormFieldListeners(Button submitButton,
                                               BigDecimalField valueField,
                                               ComboBox<CurrencyDesignation> currencyBox
    ) {
        Runnable validateFields = () -> {
            boolean isValid = valueField.getValue() != null && currencyBox.getValue() != null;
            submitButton.setEnabled(isValid);
        };
        valueField.addValueChangeListener(_ -> validateFields.run());
        currencyBox.addValueChangeListener(_ -> validateFields.run());
    }
}
