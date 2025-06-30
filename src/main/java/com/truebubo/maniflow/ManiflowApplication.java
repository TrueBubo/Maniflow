package com.truebubo.maniflow;

import com.truebubo.maniflow.ui_mode.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;

import static java.util.Arrays.stream;

/// Enter point to the application
@SpringBootApplication
public class ManiflowApplication {
    public static final String appName = "Maniflow";
    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm");
    public static final int decimalRoundingDigits = 2;
    /// Function to be executed
    ///
    /// @param args CLI arguments
    public static void main(String[] args) {
        // Remove initialization mongo logs
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR);

        UIMode mode = stream(args).anyMatch(arg -> arg.equals("-g") || arg.equals("--gui"))
                ? new GUI()
                : new CLI();

        mode.start(args);
    }

}
