package com.truebubo.maniflow.ui_mode;

import com.truebubo.maniflow.database.MongoDb;
import com.truebubo.maniflow.debt.*;
import com.truebubo.maniflow.expense.*;
import com.truebubo.maniflow.income.*;
import com.truebubo.maniflow.money.CurrencyDesignation;
import com.truebubo.maniflow.options.CLIOptions;
import com.truebubo.maniflow.stats.StatsService;
import com.truebubo.maniflow.stats.StatsViewCLI;
import com.truebubo.maniflow.stock.*;
import com.truebubo.maniflow.maniflow.Help;
import org.apache.commons.cli.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

/// Implements the CLI front-end for Maniflow system
public final class CLI implements UIMode {
    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final DebtService debtService;
    private final StockService stockService;
    private final StatsService statsService;

    /// Initializes services
    public CLI() {
        IncomeRepository incomeRepository = new MongoIncomeRepository(MongoDb.getInstance());
        ExpenseRepository expenseRepository = new MongoExpenseRepository(MongoDb.getInstance());
        DebtRepository debtRepository = new MongoDebtRepository(MongoDb.getInstance());
        StockRepository stockRepository = new MongoStockRepository(MongoDb.getInstance());

        incomeService = new IncomeService(incomeRepository);
        expenseService = new ExpenseService(expenseRepository);
        debtService = new DebtService(debtRepository);
        stockService = new StockService(stockRepository, PolygonStockPriceFinder.get(),
                incomeRepository, expenseRepository
        );

        statsService = new StatsService(incomeService, expenseService, stockService, debtService);
    }


    public void start(String[] args) {

        Optional<CurrencyDesignation> currencyDesignation = Optional.empty();
        Optional<Integer> id = Optional.empty();
        Optional<Integer> repeatsAfterDays = Optional.empty();
        Optional<BigDecimal> yearlyInterest = Optional.empty();

        Options options = CLIOptions.get();
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            if (commandLine.hasOption(CLIOptions.SupportedOptions.HELP.option)) {
                Help.showHelpCLI(options);
                return;
            }
            if (commandLine.hasOption(CLIOptions.SupportedOptions.VERSION.option)) {
                Help.showVersionCLI();
                return;
            }
            if (commandLine.hasOption(CLIOptions.SupportedOptions.STATS.option)) {
                new StatsViewCLI(this.statsService).showStats();
                return;
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.ID.option)) {
                id = Optional.of(commandLine.getParsedOptionValue(CLIOptions.SupportedOptions.ID.option));
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.CURRENCY.option)) {
                currencyDesignation = Optional.of(
                        CurrencyDesignation.fromString(commandLine.getParsedOptionValue(CLIOptions.SupportedOptions.CURRENCY.option)
                        )
                );
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.REPEATS_AFTER.option)) {
                repeatsAfterDays = Optional.of(commandLine.getParsedOptionValue(CLIOptions.SupportedOptions.REPEATS_AFTER.option));
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.INTEREST.option)) {
                yearlyInterest = Optional.of(commandLine.getParsedOptionValue(CLIOptions.SupportedOptions.INTEREST.option));
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.INCOME.option)) {
                BigDecimal value = commandLine.getParsedOptionValue(CLIOptions.SupportedOptions.INCOME.option);

                new IncomeViewCLI(this.incomeService).addIncome(
                        new Income(value, currencyDesignation.orElseThrow(() ->
                                new RuntimeException("Currency designation is required for adding incomes")),
                                Instant.now(), repeatsAfterDays.orElse(null)
                        ));
                return;
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.LIST_INCOME.option)) {
                new IncomeViewCLI(this.incomeService).showIncomes();
                return;
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.CHANGE_INCOME.option)) {
                BigDecimal value = commandLine.getParsedOptionValue(CLIOptions.SupportedOptions.CHANGE_INCOME.option);
                new IncomeViewCLI(this.incomeService).changeIncome(id.orElseThrow(() ->
                                new RuntimeException("Changing incomes requires an id to be specified")),
                        value
                );
                return;
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.EXPENSE.option)) {
                BigDecimal value = commandLine.getParsedOptionValue(CLIOptions.SupportedOptions.EXPENSE.option);
                new ExpenseViewCLI(this.expenseService).addExpense(
                        new Expense(value, currencyDesignation.orElseThrow(() ->
                                new RuntimeException("Currency designation is required for adding incomes")),
                                Instant.now(), repeatsAfterDays.orElse(null))
                );
                return;
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.LIST_EXPENSE.option)) {
                new ExpenseViewCLI(this.expenseService).showExpenses();
                return;
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.CHANGE_EXPENSE.option)) {
                BigDecimal value = commandLine.getParsedOptionValue(CLIOptions.SupportedOptions.CHANGE_EXPENSE.option);
                new ExpenseViewCLI(this.expenseService).changeExpense(id.orElseThrow(() ->
                                new RuntimeException("Changing expenses requires an id to be specified")),
                        value
                );
                return;
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.DEBT.option)) {
                BigDecimal value = commandLine.getParsedOptionValue(CLIOptions.SupportedOptions.DEBT.option);
                new DebtViewCLI(this.debtService).addDebt(
                        new Debt(value, currencyDesignation.orElseThrow(() ->
                                new RuntimeException("Currency designation is required for adding debts")),
                                yearlyInterest.orElseThrow(() -> new RuntimeException("Yearly interest is required for adding debts")),
                                Instant.now()
                        )
                );
                return;
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.LIST_DEBT.option)) {
                new DebtViewCLI(this.debtService).showDebts();
                return;
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.PAY_DEBT.option)) {
                BigDecimal value = commandLine.getParsedOptionValue(CLIOptions.SupportedOptions.PAY_DEBT.option);
                new DebtViewCLI(this.debtService).payDebt(id.orElseThrow(() ->
                                new RuntimeException("Changing incomes requires an id to be specified")),
                        value
                );
                return;
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.LIST_STOCKS.option)) {
                new StockViewCLI(stockService).showOwnedStocks();
                return;
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.BUY_STOCK.option)) {
                String input = commandLine.getOptionValue(CLIOptions.SupportedOptions.BUY_STOCK.option);
                String[] result = input.split("-");
                if (result.length != 2) {
                    System.err.println("Buying stock requires option in the format {ticket}-{quantity}");
                    return;
                }
                String ticket = result[0];
                try {
                    BigDecimal quantity = BigDecimal.valueOf(Double.parseDouble(result[1]));
                    new StockViewCLI(this.stockService).buyStock(new Stock(ticket, quantity));
                } catch (NumberFormatException e) {
                    System.err.println("Unable to parse quantity: " + result[1]);
                } catch (StockNotFoundException e) {
                    System.err.println("Unable to find stock: " + ticket);
                }

                return;
            }

            if (commandLine.hasOption(CLIOptions.SupportedOptions.SELL_STOCK.option)) {
                String input = commandLine.getOptionValue(CLIOptions.SupportedOptions.SELL_STOCK.option);
                String[] result = input.split("-");
                if (result.length != 2) {
                    System.err.println("Selling stock requires option in the format {ticket}-{quantity}");
                    return;
                }
                String ticket = result[0];
                try {
                    BigDecimal quantity = BigDecimal.valueOf(Double.parseDouble(result[1]));
                    new StockViewCLI(this.stockService).sellStock(new Stock(ticket, quantity));
                } catch (NumberFormatException e) {
                    System.err.println("Unable to parse quantity: " + result[1]);
                } catch (StockNotFoundException _) {
                    System.err.println("Unable to find stock: " + ticket);
                } catch (TooFewStocksOwnedException e) {
                    System.err.println(e.getMessage());
                }
                return;
            }

            Help.showHelpCLI(options);


        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
    }
}
