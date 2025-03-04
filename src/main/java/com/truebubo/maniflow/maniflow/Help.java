package com.truebubo.maniflow.maniflow;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public final class Help {
    private Help() {}
    public static void showHelpCLI(Options options) {
        try (var resourceStream = Help.class.getResourceAsStream("/app.properties")) {
            final Properties properties = new Properties();
            properties.load(resourceStream);
            final String appName = properties.getProperty("app.name");
            final String version = properties.getProperty("app.version");
            final String author = properties.getProperty("app.author");
            final String license = properties.getProperty("app.license");
            final HelpFormatter formatter = new HelpFormatter();
            final String appID = String.format("%s v%s", appName, version);
            final String header = String.format("%s released under %s", appName, license);
            final String footer = String.format("Created by %s", author);
            formatter.printHelp(appID, header, options, footer);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
