package com.truebubo.maniflow.ui_mode;

import com.truebubo.maniflow.ManiflowApplication;
import org.springframework.boot.SpringApplication;

/// GUI front-end for maniflow system
public final class GUI implements UIMode {
    /// Initializes services and GUI
    public GUI() {
    }

    public void start(String[] args) {
        SpringApplication.run(ManiflowApplication.class, args);
    }
}
