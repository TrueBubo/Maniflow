package com.truebubo.maniflow.Maniflow;

import com.truebubo.maniflow.Options.CLIOptions;
import com.truebubo.maniflow.UtilTests.UtilTests;
import org.apache.commons.cli.Options;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelpTest {
    @Test
    void showHelpCLI() {
        Options options = CLIOptions.get();
        String output = UtilTests.capturePrintedOutput(() -> Help.showHelpCLI(options));

        assertTrue(output.contains("stats"));
        assertTrue(output.contains("Maniflow"));
        assertTrue(output.contains("-h"));
        assertTrue(output.contains("--help"));
        assertTrue(output.contains("Pay debt"));
        assertTrue(output.contains("Created by"));
    }
}