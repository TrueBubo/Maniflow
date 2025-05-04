package com.truebubo.maniflow.Options;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

/// Used for handling of CLI arguments given to the application
public final class CLIOptions {
    /// Supported options with the associated option property for type-safe querying
    public enum SupportedOptions {
        /// Show help
        HELP(Option.builder()
                .desc("Show help")
                .option("h")
                .longOpt("help")
                .build()),

        /// Show version
        VERSION(Option.builder()
                .desc("Show version")
                .option("v")
                .longOpt("version")
                .build()),

        /// Show statistics
        STATS(Option.builder()
                .desc("Show statistics")
                .option("s")
                .longOpt("stats")
                .build()),

        /// Set the entry effected
        ID(Option.builder()
                .desc("Set the entry effected")
                .longOpt("id")
                .type(Integer.class)
                .hasArg()
                .argName("ID of the entry to be effected")
                .build()),

        /// Set the currency used
        CURRENCY(Option.builder()
                .desc("Set the currency used")
                .option("c")
                .longOpt("currency")
                .type(String.class)
                .hasArg()
                .argName("Currency code")
                .build()),

        /// Add income
        INCOME(Option.builder()
                .desc("Add income (Must be combined with a currency)")
                .longOpt("add-income")
                .hasArg()
                .type(BigDecimal.class)
                .argName("Income")
                .build()),

        /// Repeats after this many days
        REPEATS_AFTER(Option.builder()
                .desc("Repeats after this many days")
                .option("r")
                .longOpt("repeat")
                .type(Integer.class)
                .hasArg()
                .argName("Days")
                .build()
        ),

        /// List incomes
        LIST_INCOME(Option.builder()
                .desc("List incomes")
                .longOpt("list-incomes")
                .build()),

        /// Change income
        CHANGE_INCOME(Option.builder()
                .desc("Change income (Must be combined with an id)")
                .longOpt("change-income")
                .type(BigDecimal.class)
                .hasArg()
                .argName("New income")
                .build()),

        /// Add expense
        EXPENSE(Option.builder()
                .longOpt("add-expense")
                .type(BigDecimal.class)
                .hasArg()
                .argName("Expense")
                .desc("Add expense")
                .build()),

        /// List expenses
        LIST_EXPENSE(Option.builder()
                .desc("List expenses")
                .longOpt("list-expenses")
                .build()),

        /// Change expense
        CHANGE_EXPENSE(Option.builder()
                .desc("Change expense")
                .longOpt("change-expense")
                .type(BigDecimal.class)
                .hasArg()
                .argName("New expense")
                .build()),

        /// Add debt
        DEBT(Option.builder()
                .desc("Add debt (Must be combined with a currency and yearly interest)")
                .longOpt("add-debt")
                .type(BigDecimal.class)
                .hasArg()
                .argName("New debt")
                .build()),

        /// Yearly interest
        INTEREST(Option.builder()
                .desc("Yearly interest")
                .option("yi")
                .longOpt("yearly-interest")
                .type(BigDecimal.class)
                .hasArg()
                .argName("Interest rate")
                .build()),

        /// List debts
        LIST_DEBT(Option.builder()
                .desc("List debts")
                .option("ld")
                .longOpt("list-debts")
                .build()),

        /// Pay debt
        PAY_DEBT(Option.builder()
                .desc("Pay debt")
                .option("pd")
                .longOpt("pay-debts")
                .type(BigDecimal.class)
                .hasArg()
                .argName("Amount paid")
                .build()),

        /// List stocks
        LIST_STOCKS(Option.builder()
                .desc("List owned stocks")
                .longOpt("list-stocks")
                .build()),

        /// Buy stocks
        BUY_STOCK(Option.builder()
                .desc("Buy stocks (Accepts format {ticket}-{quantity})")
                .longOpt("buy-stock")
                .type(String.class)
                .hasArg()
                .argName("Stock")
                .build()),

        /// Sell stock
        SELL_STOCK(Option.builder()
                .desc("Buy stock (Accepts format {ticket}-{quantity}")
                .longOpt("sell-stock")
                .type(String.class)
                .hasArg()
                .argName("Stock info")
                .build());

        /// Option for type-safe querying
        public final Option option;

        SupportedOptions(Option option) {
            this.option = option;
        }
    }

    private static Options options;

    private CLIOptions() {
    }

    /// Gets options
    ///
    /// @return Lazily loaded option
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
