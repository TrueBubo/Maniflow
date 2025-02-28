package com.truebubo.maniflow.UIMode;

public sealed interface UIMode permits CLI, GUI {
    static void start(String[] args) {}
}
