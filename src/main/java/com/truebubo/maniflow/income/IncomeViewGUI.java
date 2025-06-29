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
import static com.truebubo.maniflow.commongui.CommonGUI.*;

/// GUI Frontend for income portion of the application
@UIScope
@Component
public class IncomeViewGUI extends VerticalLayout {
    private final IncomeService incomeService;

    public IncomeViewGUI(@NonNull IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    /// Sets up the income div with its components
    @PostConstruct
    public void init() {
        removeAll();
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
                getIncomesParagraph(incomeService.getIncomes().reversed())
        );

        add(verticalLayout);
    }


    private FormLayout getIncomeFormLayout() {
        final var valueField = getValueField("Income",
                value -> value != null && value.compareTo(BigDecimal.ZERO) < 0
        );
        final var currencyBox = getCurrencyBox("Income");
        final var repeatsAfterDaysField = getRepeatsAfterDaysField("Income");
        final var submitButton = getSubmitButton("Income");
        addSubmitHandler(submitButton, incomeService, valueField, currencyBox, repeatsAfterDaysField);
        setUpFormFieldListeners(submitButton, valueField, currencyBox);
        return getFormLayout(submitButton, valueField, currencyBox, repeatsAfterDaysField);
    }

    private static Paragraph getIncomesParagraph(List<Income> incomes) {
        final var incomesParagraph = new Paragraph();
        incomesParagraph.getStyle().set("white-space", "pre-line");
        incomesParagraph.setId("incomesList");

        incomes.forEach(income -> {
            final var roundedValue = income.value().setScale(decimalRoundingDigits, RoundingMode.HALF_DOWN);
            final var time = timeFormatter.format(income.created().atZone(ZoneId.systemDefault()));
            ;
            final var repeatSuffix = (income.repeatsAfterDays() != null) ? " every " + income.repeatsAfterDays() + " days" : "";
            incomesParagraph.add(
                    time + " - " + roundedValue + income.currencyDesignation() + repeatSuffix + "\n"
            );
        });

        return incomesParagraph;
    }

    private void addSubmitHandler(Button submitButton, IncomeService incomeService, BigDecimalField valueField, ComboBox<CurrencyDesignation> currencyBox, IntegerField repeatsAfterDaysField) {
        submitButton.addClickListener(_ -> {
            incomeService.addIncome(
                    new Income(
                            valueField.getValue(),
                            currencyBox.getValue(),
                            Instant.now(),
                            repeatsAfterDaysField.getValue()
                    )
            );
            init();
            Notification.show("New income submitted!");
        });
    }
}
