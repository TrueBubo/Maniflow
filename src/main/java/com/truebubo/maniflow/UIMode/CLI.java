package com.truebubo.maniflow.UIMode;

import com.truebubo.maniflow.Options.CLIOptions;
import com.truebubo.maniflow.maniflow.Help;
import org.apache.commons.cli.*;

public final class CLI implements UIMode {
    private CLI() {
    }

    public static void start(String[] args) {
        Options options = CLIOptions.get();
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            if (commandLine.hasOption("h")) {
                Help.showHelpCLI(options);
                return;
            }
            if (commandLine.hasOption("v")) {
                Help.showVersionCLI();
                return;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
