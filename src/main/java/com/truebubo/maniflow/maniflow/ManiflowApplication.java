package com.truebubo.maniflow.maniflow;

import com.truebubo.maniflow.UIMode.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;


import static java.util.Arrays.stream;

@SpringBootApplication
public class ManiflowApplication {
    public static void main(String[] args) {
        // Remove initialization mongo logs
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR);

        UIMode mode = stream(args).anyMatch(arg -> arg.equals("-g") || arg.equals("--gui"))
            ? new GUI()
            : new CLI();

        mode.start(args);
    }

}
