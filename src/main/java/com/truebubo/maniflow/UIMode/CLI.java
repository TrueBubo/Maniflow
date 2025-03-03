package com.truebubo.maniflow.UIMode;

import com.truebubo.maniflow.Options.CLIOptions;
import org.apache.commons.cli.Options;

public final class CLI implements UIMode {
    private CLI() {
    }

    public static void start(String[] args) {
        System.out.println("CLI");

        Options options = CLIOptions.get(args);
    }

    private static void dispatch(Options options) {

    }
}
