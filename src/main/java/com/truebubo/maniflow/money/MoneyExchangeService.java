package com.truebubo.maniflow.money;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;

public interface MoneyExchangeService<T extends MoneyExchange> {
    List<T> get();
    void add(@NonNull T moneyExchange);
    void change(int id, @NonNull BigDecimal moneyExchange);
}
