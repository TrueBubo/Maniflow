package com.truebubo.maniflow;

import com.truebubo.maniflow.stats.StatsViewGUI;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import org.springframework.lang.NonNull;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;


import static com.truebubo.maniflow.ManiflowApplication.appName;

@Route("")
public class MainViewGUI extends VerticalLayout {
    private final StatsViewGUI statsViewGUI;
    public MainViewGUI(@NonNull StatsViewGUI statsViewGUI) {
       this.statsViewGUI = statsViewGUI;
    }

    @PostConstruct
    public void init() {
        final var title = new H1(appName);
        final var statsTitle = new H2("Statistics");
        VerticalLayout verticalLayout = new VerticalLayout(
                title,
                statsTitle,
                statsViewGUI
        );
        verticalLayout.setWidthFull();
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);


        add(verticalLayout);

    }
}
