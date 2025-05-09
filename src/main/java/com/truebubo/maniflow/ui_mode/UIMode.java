package com.truebubo.maniflow.ui_mode;

/// Front-end for Maniflow system
public sealed interface UIMode permits CLI, GUI {
    /// Starts the application with given arguments in the selected mode
    ///
    /// @param args Args passed when starting the application
    void start(String[] args);
}
