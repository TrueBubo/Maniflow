package com.truebubo.maniflow.UIMode;

public sealed interface UIMode permits CLI, GUI {
    void start(String[] args);
}
