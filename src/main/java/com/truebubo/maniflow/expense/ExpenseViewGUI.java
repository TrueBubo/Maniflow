package com.truebubo.maniflow.expense;

import com.truebubo.maniflow.income.Income;
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
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static com.truebubo.maniflow.ManiflowApplication.decimalRoundingDigits;
import static com.truebubo.maniflow.ManiflowApplication.timeFormatter;
import static com.truebubo.maniflow.commongui.CommonGUI.*;

/// GUI Frontend for expense portion of the application
@UIScope
@Component
public class ExpenseViewGUI extends VerticalLayout {
    private final ExpenseService expenseService;

    public ExpenseViewGUI(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /// Sets up the expense div with its components
    @PostConstruct
    public void init() {
        removeAll();
        final var verticalLayout = new VerticalLayout();
        verticalLayout.setId("expenseLayout");

        final var addExpenseTitle = new H2("Add expense");
        addExpenseTitle.setId("addExpenseTitle");

        final var expensesTitle = new H2("Expenses");
        expensesTitle.setId("expensesTitle");

        verticalLayout.add(
                addExpenseTitle,
                getExpenseFormLayout(),
                expensesTitle,
                getExpensesParagraph(expenseService.getExpenses().reversed())
        );

        add(verticalLayout);
    }

    private FormLayout getExpenseFormLayout() {
        final var valueField = getValueField("Expense", value -> value != null && value.compareTo(BigDecimal.ZERO) < 0);
        final var currencyBox = getCurrencyBox("Expense");
        final var repeatsAfterDaysField = getRepeatsAfterDaysField("Expense");
        final var submitButton = getSubmitButton("Expense");
        addSubmitHandler(submitButton, expenseService, valueField, currencyBox, repeatsAfterDaysField);
        setUpFormFieldListeners(submitButton, valueField, currencyBox);
        return getFormLayout(submitButton, valueField, currencyBox, repeatsAfterDaysField);
    }

    private static Paragraph getExpensesParagraph(List<Expense> incomes) {
        final var expensesParagraph = new Paragraph();
        expensesParagraph.getStyle().set("white-space", "pre-line");
        expensesParagraph.setId("expenseList");

        incomes.forEach(expense -> {
            final var roundedValue = expense.value().setScale(decimalRoundingDigits, RoundingMode.HALF_DOWN);
            final var time = timeFormatter.format(expense.created().atZone(ZoneId.systemDefault()));
            final var repeatSuffix = (expense.repeatsAfterDays() != null) ? " every " + expense.repeatsAfterDays() + " days" : "";
            expensesParagraph.add(
                    time + " - " + roundedValue + expense.currencyDesignation() + repeatSuffix + "\n"
            );
        });

        return expensesParagraph;
    }


    private void addSubmitHandler(Button submitButton, ExpenseService expenseService, BigDecimalField valueField, ComboBox<CurrencyDesignation> currencyBox, IntegerField repeatsAfterDaysField) {
        submitButton.addClickListener(_ -> {
            expenseService.addExpense(
                    new Expense(
                            valueField.getValue(),
                            currencyBox.getValue(),
                            Instant.now(),
                            repeatsAfterDaysField.getValue()
                    )
            );
            init();
            Notification.show("New expense submitted!");
        });
    }


}
