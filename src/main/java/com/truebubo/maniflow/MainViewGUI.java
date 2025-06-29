package com.truebubo.maniflow;

import com.truebubo.maniflow.debt.DebtViewGUI;
import com.truebubo.maniflow.expense.ExpenseViewGUI;
import com.truebubo.maniflow.income.IncomeViewGUI;
import com.truebubo.maniflow.stats.StatsViewGUI;
import com.truebubo.maniflow.stock.StockViewGUI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import org.springframework.lang.NonNull;


import static com.truebubo.maniflow.ManiflowApplication.appName;

/// Main page of the application
@Route("")
public class MainViewGUI extends VerticalLayout {
    private final StatsViewGUI statsViewGUI;
    private final IncomeViewGUI incomeViewGUI;
    private final ExpenseViewGUI expenseViewGUI;
    private final DebtViewGUI debtViewGUI;
    private final StockViewGUI stockViewGUI;

    public MainViewGUI(@NonNull StatsViewGUI statsViewGUI,
                       @NonNull IncomeViewGUI incomeViewGUI,
                       @NonNull ExpenseViewGUI expenseViewGUI, DebtViewGUI debtViewGUI, StockViewGUI stockViewGUI) {
        this.statsViewGUI = statsViewGUI;
        this.incomeViewGUI = incomeViewGUI;
        this.expenseViewGUI = expenseViewGUI;
        this.debtViewGUI = debtViewGUI;
        this.stockViewGUI = stockViewGUI;
    }

    /// Sets up the page
    @PostConstruct
    public void init() {
        final var verticalLayout = new VerticalLayout();
        verticalLayout.setId("app");


        final var title = new H1(appName);
        final var tabSheet = new TabSheet();
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_CENTERED);

        tabSheet.add("Statistics", statsViewGUI);
        tabSheet.add("Income", incomeViewGUI);
        tabSheet.add("Expense", expenseViewGUI);
        tabSheet.add("Stock", stockViewGUI);
        tabSheet.add("Debt", debtViewGUI);


        tabSheet.addSelectedChangeListener(event -> {
            String selectedTabLabel = event.getSelectedTab().getLabel();
            if ("Statistics".equals(selectedTabLabel)) {
                statsViewGUI.init();
            }
            else if ("Income".equals(selectedTabLabel)) {
                incomeViewGUI.init();
            }
            else if ("Expense".equals(selectedTabLabel)) {
                expenseViewGUI.init();
            }
        });

        verticalLayout.add(title, tabSheet);
        verticalLayout.setWidthFull();
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(verticalLayout);
    }
}
