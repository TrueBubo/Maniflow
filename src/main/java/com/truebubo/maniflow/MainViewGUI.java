package com.truebubo.maniflow;

import com.truebubo.maniflow.income.IncomeViewGUI;
import com.truebubo.maniflow.stats.StatsViewGUI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import org.springframework.lang.NonNull;


import static com.truebubo.maniflow.ManiflowApplication.appName;

/// Main page of the application
@Route("")
public class MainViewGUI extends VerticalLayout {
    private final StatsViewGUI statsViewGUI;
    private final IncomeViewGUI incomeViewGUI;
    public MainViewGUI(@NonNull StatsViewGUI statsViewGUI, @NonNull IncomeViewGUI incomeViewGUI) {
       this.statsViewGUI = statsViewGUI;
       this.incomeViewGUI = incomeViewGUI;
    }

    /// Sets up the page
    @PostConstruct
    public void init() {
        final var verticalLayout = new VerticalLayout();
        verticalLayout.setId("app");

        final var title = new H1(appName);
        final var tabSheet = new TabSheet();

        tabSheet.add("Statistics", statsViewGUI);
        tabSheet.add("Income", incomeViewGUI);


        tabSheet.addSelectedChangeListener(event -> {
            String selectedTabLabel = event.getSelectedTab().getLabel();
            System.out.println("Selected tab: " + selectedTabLabel);

            if ("Statistics".equals(selectedTabLabel)) {
                statsViewGUI.init();
            }
        });

        verticalLayout.add(title, tabSheet);
        verticalLayout.setWidthFull();
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(verticalLayout);
    }
}
