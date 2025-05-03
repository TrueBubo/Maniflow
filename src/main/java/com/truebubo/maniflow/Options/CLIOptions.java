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
                .type(Long.class)
                .argName("ID of the entry to be effected")
                .build()),

        CURRENCY(Option.builder()
                .desc("Set the currency used")
                .option("c")
                .longOpt("currency")
                .argName("Currency code")
                .build()),

        INCOME(Option.builder()
                .desc("Add income")
                .option("i")
                .longOpt("add-income")
                .type(BigDecimal.class)
                .argName("Income")
                .build()),

        REPEATS_AFTER(Option.builder()
                .desc("Repeats after this many days")
                .option("r")
                .longOpt("repeat")
                .type(Integer.class)
                .argName("Days")
                .build()
        ),

        LIST_INCOME(Option.builder()
                .desc("List incomes")
                .option("li")
                .longOpt("list-incomes")
                .build()),

        CHANGE_INCOME(Option.builder()
                .desc("Change income")
                .option("ci")
                .longOpt("change-income")
                .type(BigDecimal.class)
                .argName("New income")
                .build()),

        EXPENSE(Option.builder()
                .option("e")
                .longOpt("add-expense")
                .type(BigDecimal.class)
                .argName("Expense")
                .desc("Add expense")
                .build()),

        LIST_EXPENSE(Option.builder()
                .desc("List expenses")
                .option("le")
                .longOpt("list-expenses")
                .build()),

        CHANGE_EXPENSE(Option.builder()
                .desc("Change expense")
                .option("ce")
                .longOpt("change-expense")
                .type(BigDecimal.class)
                .argName("New expense")
                .build()),

        DEBT(Option.builder()
                .desc("Add debt")
                .option("db")
                .longOpt("add-debt")
                .type(BigDecimal.class)
                .argName("New debt")
                .build()),

        INTEREST(Option.builder()
                .desc("Yearly interest")
                .option("yi")
                .longOpt("yearly-interest")
                .type(BigDecimal.class)
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
                .argName("Amount paid")
                .build()),

        BUY_STOCK(Option.builder()
                .desc("List owned stocks (Accepts format {ticket}-{quantity})")
                .option("ls")
                .longOpt("list-stock")
                .type(String.class)
                .argName("Stock")
                .build()),

        SELL_STOCK(Option.builder()
                .desc("Buy stock (Accepts format {ticket}-{quantity}")
                .option("ss")
                .longOpt("sell-stock")
                .type(String.class)
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
