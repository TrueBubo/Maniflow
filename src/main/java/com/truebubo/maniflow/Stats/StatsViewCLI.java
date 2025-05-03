package com.truebubo.maniflow.Stats;

/// CLI Frontend for expense portion of the application
public class StatsViewCLI {
    private final StatsService statsService;
    public StatsViewCLI(StatsService statsService) {
        this.statsService = statsService;
    }

    /// Display summary of our finances
    public void showMoneyStats() {
        statsService.getMoneyStats();
    }
}
