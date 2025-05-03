package com.truebubo.maniflow.Options;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

/// Used for handling of CLI arguments given to the application
public final class CLIOptions {
    public enum SupportedOptions {
        HELP(Option.builder()
                .desc("Show help")
                .option("h")
                .longOpt("help")
                .build()),

        VERSION(Option.builder()
                .desc("Show version")
                .option("v")
                .longOpt("version")
                .build()),

        STATS(Option.builder()
                .desc("Show statistics")
                .option("s")
                .longOpt("stats")
                .build()),

        ID(Option.builder()
                .desc("Set the entry effected")
                .longOpt("id")
                .type(Integer.class)
                .hasArg()
                .argName("ID of the entry to be effected")
                .build()),

        CURRENCY(Option.builder()
                .desc("Set the currency used")
                .option("c")
                .longOpt("currency")
                .type(String.class)
                .hasArg()
                .argName("Currency code")
                .build()),

        INCOME(Option.builder()
                .desc("Add income (Must be combined with a currency)")
                .longOpt("add-income")
                .hasArg()
                .type(BigDecimal.class)
                .argName("Income")
                .build()),

        REPEATS_AFTER(Option.builder()
                .desc("Repeats after this many days")
                .option("r")
                .longOpt("repeat")
                .type(Integer.class)
                .hasArg()
                .argName("Days")
                .build()
        ),

        LIST_INCOME(Option.builder()
                .desc("List incomes")
                .longOpt("list-incomes")
                .build()),

        CHANGE_INCOME(Option.builder()
                .desc("Change income (Must be combined with an id)")
                .longOpt("change-income")
                .type(BigDecimal.class)
                .hasArg()
                .argName("New income")
                .build()),

        EXPENSE(Option.builder()
                .longOpt("add-expense")
                .type(BigDecimal.class)
                .hasArg()
                .argName("Expense")
                .desc("Add expense")
                .build()),

        LIST_EXPENSE(Option.builder()
                .desc("List expenses")
                .longOpt("list-expenses")
                .build()),

        CHANGE_EXPENSE(Option.builder()
                .desc("Change expense")
                .longOpt("change-expense")
                .type(BigDecimal.class)
                .hasArg()
                .argName("New expense")
                .build()),

        DEBT(Option.builder()
                .desc("Add debt (Must be combined with a currency and yearly interest)")
                .longOpt("add-debt")
                .type(BigDecimal.class)
                .hasArg()
                .argName("New debt")
                .build()),

        INTEREST(Option.builder()
                .desc("Yearly interest")
                .option("yi")
                .longOpt("yearly-interest")
                .type(BigDecimal.class)
                .hasArg()
                .argName("Interest rate")
                .build()),

        LIST_DEBT(Option.builder()
                .desc("List debts")
                .option("ld")
                .longOpt("list-debts")
                .build()),

        PAY_DEBT(Option.builder()
                .desc("Pay debt")
                .option("pd")
                .longOpt("pay-debts")
                .type(BigDecimal.class)
                .hasArg()
                .argName("Amount paid")
                .build()),

        LIST_STOCKS(Option.builder()
                .desc("List owned stocks")
                .longOpt("list-stocks")
                .build()),

        BUY_STOCK(Option.builder()
                .desc("Buy stocks (Accepts format {ticket}-{quantity})")
                .longOpt("buy-stock")
                .type(String.class)
                .hasArg()
                .argName("Stock")
                .build()),

        SELL_STOCK(Option.builder()
                .desc("Buy stock (Accepts format {ticket}-{quantity}")
                .longOpt("sell-stock")
                .type(String.class)
                .hasArg()
                .argName("Stock info")
                .build());

        public final Option option;

        SupportedOptions(Option option) {
            this.option = option;
        }
    }

    private static Options options;

    private CLIOptions() {
    }

    /// Gets options
    public static Options get() {
        if (options == null) {
            options = new Options();
            setParsedOptions();
        }
        return options;
    }

    /// Parses all the options given
    private static void setParsedOptions() {
        Arrays.stream(SupportedOptions.values()).forEach(option -> {
            options.addOption(option.option);
        });
    }
}
