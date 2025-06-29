package com.truebubo.maniflow;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import static com.truebubo.maniflow.ManiflowApplication.appName;


@Theme(variant = Lumo.DARK)
@PWA(name = appName, shortName = appName, iconPath = "Maniflow_logo.png")
public class AppShell implements AppShellConfigurator {
}
