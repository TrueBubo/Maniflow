package com.truebubo.maniflow.debt;

import com.truebubo.maniflow.money.CurrencyDesignation;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static com.truebubo.maniflow.ManiflowApplication.decimalRoundingDigits;
import static com.truebubo.maniflow.ManiflowApplication.timeFormatter;
import static com.truebubo.maniflow.commongui.CommonGUI.*;
import static com.truebubo.maniflow.commongui.CommonGUI.getFormLayout;
import static com.truebubo.maniflow.commongui.CommonGUI.getSubmitButton;
import static com.truebubo.maniflow.commongui.CommonGUI.setUpFormFieldListeners;

/// GUI Frontend for debt portion of the application
@UIScope
@Component
public class DebtViewGUI extends VerticalLayout {
    private final DebtService debtService;
    public DebtViewGUI(DebtService debtService) {
        this.debtService = debtService;
    }

    /// Sets up the expense div with its components
    @PostConstruct
    public void init() {
        removeAll();
        final var verticalLayout = new VerticalLayout();
        verticalLayout.setId("debtLayout");

        final var addDebtTitle = new H2("Add debt");
        addDebtTitle.setId("addDebtTitle");

        final var expensesTitle = new H2("Debts");
        expensesTitle.setId("debtsTitle");

        verticalLayout.add(
                addDebtTitle,
                getDebtFormLayout(),
                expensesTitle,
                getDebtsDiv(debtService.getDebts())
        );

        add(verticalLayout);
    }

    private Div getDebtsDiv(List<Debt> debts) {
        final var debtsDiv = new Div();
        debtsDiv.getStyle().set("white-space", "pre-line");
        debtsDiv.setId("debtList");

        debts.reversed().forEach(debt -> {
            Div row = new Div();
            row.getStyle()
                    .set("display", "flex")
                    .set("justify-content", "space-between");

            final var time = timeFormatter.format(debt.created().atZone(ZoneId.systemDefault()));
            final var roundedDebt = debt.getValueWithInterest().setScale(decimalRoundingDigits, RoundingMode.HALF_DOWN);
            final var text = new Span(time + " - " + roundedDebt + debt.currencyDesignation() + " " + debt.yearlyInterest() + "%\n");
            final var payDebtLabel = new Span("Pay");
            payDebtLabel.getStyle().set("color", "gray").setPaddingLeft("3em").setPaddingRight("0.5em");
            final var payDebtField = new BigDecimalField();
            payDebtField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            payDebtField.getStyle().setPadding("0 0");

            payDebtField.setSuffixComponent(new Span(String.valueOf(debt.currencyDesignation())));
            payDebtField.addKeyPressListener(Key.ENTER, _ -> {
                final var debtPaid = payDebtField.getValue();
                debtService.payDebt(debts.indexOf(debt) + 1, debtPaid);
                init();
            });

            row.add(text, payDebtLabel, payDebtField);

            debtsDiv.add(row);
        });

        return debtsDiv;
    }

    private FormLayout getDebtFormLayout() {
        final var valueField = getValueField("Debts", value -> value != null && value.compareTo(BigDecimal.ZERO) < 0);
        final var currencyBox = getCurrencyBox("Debt");
        final var repeatsAfterDaysField = getInterestField();
        final var submitButton = getSubmitButton("Debt");
        addSubmitHandler(submitButton, debtService, valueField, currencyBox, repeatsAfterDaysField);
        setUpFormFieldListeners(submitButton, valueField, currencyBox);
        return getFormLayout(submitButton, valueField, currencyBox, repeatsAfterDaysField);
    }

    public static BigDecimalField getInterestField() {
        final var interestField = new BigDecimalField("Interest");
        interestField.setId("interestField");
        interestField.setRequired(true);

        return interestField;
    }

    private void addSubmitHandler(Button submitButton, DebtService debtService, BigDecimalField valueField, ComboBox<CurrencyDesignation> currencyBox, BigDecimalField interestField) {
        submitButton.addClickListener(_ -> {
            debtService.addDebt(
                    new Debt(
                            valueField.getValue(),
                            currencyBox.getValue(),
                            interestField.getValue(),
                            Instant.now()
                    )
            );
            init();
            Notification.show("New expense submitted!");
        });
    }


}
