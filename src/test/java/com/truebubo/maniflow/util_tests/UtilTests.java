package com.truebubo.maniflow.util_tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class UtilTests {
    public static String capturePrintedOutput(Runnable runnable) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        runnable.run();

        System.setOut(originalOut);
        return outputStream.toString();


    }
}
