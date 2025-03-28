package com.truebubo.maniflow.Options;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.math.BigDecimal;

/// Used for handling of CLI arguments given to the application
public final class CLIOptions {
    public enum SupportedOptions {
        HELP("help"),
        VERSION("version"),
        STATS("stats"),
        ID("id"),
        CURRENCY("currency"),
        INCOME("add-income"),
        LIST_INCOME("list-income"),
        CHANGE_INCOME("change-income"),
        EXPANSE("add-expanse"),
        LIST_EXPANSE("list-expanse"),
        CHANGE_EXPANSE("change-expanse"),
        DEBT("add-debt"),
        INTEREST("yearly-interest"),
        LIST_DEBT("list-debt"),
        PAY_DEBT("pay-debt"),;
        public final String longOption;
        SupportedOptions(String longOption) {
            this.longOption = longOption;
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
        setProjectOptions();
        setStatsOption();
        setIDOption();
        setCurrencyOption();
        setIncomeOptions();
        setExpenseOptions();
        addDebtOptions();
    }

    private static void setProjectOptions() {
        // Show help with instruction on how to operate Maniflow
        options.addOption(
                Option.builder()
                        .desc("Show help")
                        .option("h")
                        .longOpt("help")
                        .build()
        );

        // Show the installed version of Maniflow
        options.addOption(
                Option.builder()
                        .desc("Show version")
                        .option("v")
                        .longOpt("version")
                        .build()
        );
    }

    private static void setStatsOption() {
        // Show analysis of your money handling
        options.addOption(
                Option.builder()
                        .desc("Show statistics")
                        .option("s")
                        .longOpt("stats")
                        .build()
        );
    }

    private static void setIDOption() {
        // ID of the entry effected
        options.addOption(
                Option.builder()
                        .desc("Set the entry effected")
                        .longOpt("id")
                        .type(Long.class)
                        .argName("ID of the entry to be effected")
                        .build()
        );
    }

    private static void setCurrencyOption() {
        // Sets the currency used with their code according to ISO 4217
        options.addOption(
                Option.builder()
                        .desc("Set the currency used")
                        .option("c")
                        .longOpt("currency")
                        .argName("Currency code")
                        .build()
        );
    }

    private static void setIncomeOptions() {
        // Add income
        options.addOption(
                Option.builder()
                        .desc("Add income")
                        .option("i")
                        .longOpt("add-income")
                        .type(BigDecimal.class)
                        .argName("Income")
                        .build()
        );

        // List incomes
        options.addOption(
                Option.builder()
                        .desc("List incomes")
                        .option("li")
                        .longOpt("list-incomes")
                        .build()
        );

        // Change income, changing to zero deletes the entry
        options.addOption(
                Option.builder()
                        .desc("Change income")
                        .option("ci")
                        .longOpt("change-income")
                        .type(BigDecimal.class)
                        .argName("New income")
                        .build()
        );
    }

    private static void setExpenseOptions() {
        // Add expense
        options.addOption(
                Option.builder()
                        .option("e")
                        .longOpt("add-expense")
                        .type(BigDecimal.class)
                        .argName("Expense")
                        .desc("Add expense")
                        .build()
        );

        // List expenses
        options.addOption(
                Option.builder()
                        .desc("List expenses")
                        .option("le")
                        .longOpt("list-expenses")
                        .build()
        );

        // Change expense, changing to zero deletes the entry
        options.addOption(
                Option.builder()
                        .desc("Change expense")
                        .option("ce")
                        .longOpt("change-expense")
                        .type(BigDecimal.class)
                        .argName("New expense")
                        .build()
        );
    }

    private static void addDebtOptions() {
        // Add debt
        options.addOption(
                Option.builder()
                        .desc("Add debt")
                        .option("db")
                        .longOpt("add-debt")
                        .type(BigDecimal.class)
                        .argName("New debt")
                        .build()
        );

        // Set yearly interest
        options.addOption(
                Option.builder()
                        .desc("Yearly interest")
                        .option("yi")
                        .longOpt("yearly-interest")
                        .type(BigDecimal.class)
                        .argName("Interest rate")
                        .build()
        );

        // List debts
        options.addOption(
                Option.builder()
                        .desc("List debts")
                        .option("ld")
                        .longOpt("list-debts")
                        .build()
        );

        // Pay debt
        options.addOption(
                Option.builder()
                        .desc("Pay debt")
                        .option("pd")
                        .longOpt("pay-debts")
                        .type(BigDecimal.class)
                        .argName("Amount paid")
                        .build()
        );
    }
}
