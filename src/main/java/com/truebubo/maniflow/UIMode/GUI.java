package com.truebubo.maniflow.UIMode;

public final class GUI implements UIMode {
    private GUI() {}

    public static void start(String[] args) {
        throw new UnsupportedOperationException("The GUI mode is not yet supported. Please use the CLI mode");
        /* SpringApplication.run(ManiflowApplication.class, args); */
    }
}
