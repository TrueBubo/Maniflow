package com.truebubo.maniflow.maniflow;

import com.truebubo.maniflow.UIMode.*;
import com.truebubo.maniflow.maniflow.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.util.Arrays.stream;

@SpringBootApplication
public class ManiflowApplication {
    private enum UIModeCode {
        CLI,
        GUI
    }

    public static void main(String[] args) {
        UIModeCode selectedMode = stream(args).anyMatch(arg -> arg.equals("-g") || arg.equals("--gui"))
                ? UIModeCode.GUI
                : UIModeCode.CLI;
        switch (selectedMode) {
            case UIModeCode.CLI -> CLI.start(args);
            case UIModeCode.GUI -> GUI.start(args);
        }

    }

}
