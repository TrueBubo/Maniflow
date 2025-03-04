package com.truebubo.maniflow.maniflow;

import com.truebubo.maniflow.Options.CLIOptions;
import org.apache.commons.cli.Options;
import org.junit.jupiter.api.Test;

class HelpTest {
    @Test
    void showHelpCLI() {
        Options options = CLIOptions.get(new String[]{});
        Help.showHelpCLI(options);
    }
}