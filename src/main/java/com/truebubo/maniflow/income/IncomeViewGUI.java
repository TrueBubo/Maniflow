package com.truebubo.maniflow.income;

import com.truebubo.maniflow.money.CurrencyDesignation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.PostConstruct;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static com.truebubo.maniflow.ManiflowApplication.decimalRoundingDigits;
import static com.truebubo.maniflow.ManiflowApplication.timeFormatter;

@UIScope
@Component
public class IncomeViewGUI extends VerticalLayout {
    private final IncomeService incomeService;

    public IncomeViewGUI(@NonNull IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostConstruct
    public void init() {
        final var verticalLayout = new VerticalLayout();
        verticalLayout.setId("incomeLayout");

        final var addIncomeTitle = new H2("Add income");
        addIncomeTitle.setId("addIncomeTitle");

        final var incomesTitle = new H2("Incomes");
        incomesTitle.setId("incomesTitle");

        verticalLayout.add(
                addIncomeTitle,
                getIncomeFormLayout(),
                incomesTitle,
                getIncomesParagraph(incomeService.getIncomes())
        );

        add(verticalLayout);
    }

    private FormLayout getIncomeFormLayout() {
        final var valueField = getValueField();
        final var currencyBox = getCurrencyBox();
        final var repeatsAfterDaysField = getRepeatsAfterDaysField();
        final var submitButton = getSubmitButton(incomeService, valueField, currencyBox, repeatsAfterDaysField);

        Runnable validateFields = () -> {
            boolean isValid =
                    valueField.getValue() != null &&
                    currencyBox.getValue() != null;
            submitButton.setEnabled(isValid);
        };

        valueField.addValueChangeListener(_ -> {
            final var value = valueField.getValue();
            if (value != null && value.compareTo(BigDecimal.ZERO) < 0) valueField.setValue(null);
            validateFields.run();
        });
        currencyBox.addValueChangeListener(_ -> validateFields.run());

        FormLayout formLayout = new FormLayout();
        formLayout.setAutoResponsive(true);
        formLayout.setColumnWidth("10em");
        formLayout.setExpandColumns(true);

        formLayout.addFormRow(valueField, currencyBox, repeatsAfterDaysField);
        formLayout.addFormRow(submitButton);
        return formLayout;
    }

    private static Paragraph getIncomesParagraph(List<Income> incomes) {
        final var incomesParagraph = new Paragraph();
        incomesParagraph.getStyle().set("white-space", "pre-line");
        incomesParagraph.setId("incomesList");

        incomes.forEach(income -> {
            final var roundedValue = income.value().setScale(decimalRoundingDigits, RoundingMode.HALF_DOWN);
            final var time = timeFormatter.format(income.created().atZone(ZoneId.systemDefault()));;
            final var repeatSuffix = (income.repeatsAfterDays() != null) ? " every " + income.repeatsAfterDays() + " days" : "";
            incomesParagraph.add(
                     time + " - " + roundedValue + income.currencyDesignation() + repeatSuffix + "\n"
            );
        });

        return incomesParagraph;
    }

    private static BigDecimalField getValueField() {
        final var valueField = new BigDecimalField("Value");
        valueField.setId("incomeValueField");
        valueField.setRequired(true);
        return valueField;
    }

    private static ComboBox<CurrencyDesignation> getCurrencyBox() {
        final var currencyBox = new ComboBox<CurrencyDesignation>("Currency");
        currencyBox.setItems(CurrencyDesignation.values());
        currencyBox.setId("incomeCurrencyDesignation");
        currencyBox.setRequired(true);
        return currencyBox;
    }

    private static IntegerField getRepeatsAfterDaysField() {
        final var repeatsAfterDaysField = new IntegerField("Will repeat in (days)");
        repeatsAfterDaysField.setId("incomeRepeatsAfterDays");
        repeatsAfterDaysField.setMin(0);
        repeatsAfterDaysField.setRequired(false);
        return repeatsAfterDaysField;
    }

    private static Button getSubmitButton(IncomeService incomeService, BigDecimalField valueField, ComboBox<CurrencyDesignation> currencyBox, IntegerField repeatsAfterDaysField) {
        final var submitButton = new Button("Submit");
        submitButton.setId("incomeSubmitButton");
        submitButton.setEnabled(false);

        submitButton.addClickListener(event -> {
            System.out.println("Form submitted!");
            incomeService.addIncome(
                    new Income(
                            valueField.getValue(),
                            currencyBox.getValue(),
                            Instant.now(),
                            repeatsAfterDaysField.getValue()
                    )
            );
            valueField.clear();
            currencyBox.clear();
            Notification.show("New income submitted!");
        });

        return submitButton;
    }
}
