package com.truebubo.maniflow.maniflow;

import com.truebubo.maniflow.UIMode.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.function.Consumer;

import static java.util.Arrays.stream;

@SpringBootApplication
public class ManiflowApplication {
    public static void main(String[] args) {
        Consumer<String[]> selectedMode = stream(args).anyMatch(arg -> arg.equals("-g") || arg.equals("--gui"))
                ? GUI::start
                : CLI::start;
        selectedMode.accept(args);
    }

}
