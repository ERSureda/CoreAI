package com.taxai.api.shared.domain.valueobject;

import java.util.Currency;
import java.util.Objects;

public record Money(long amount, Currency currency) implements Comparable<Money> {

    public Money {
        Objects.requireNonNull(currency, "CURRENCY_NOT_NULL");
    }

    public static Money of(long amount, String currencyCode) {
        return new Money(amount, Currency.getInstance(currencyCode));
    }

    public static Money of(long amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money zero(String currencyCode) {
        return of(0, currencyCode);
    }

    public static Money zero(Currency currency) {
        return of(0, currency);
    }

    public Money add(Money money) {
        checkSameCurrency(money);
        return new Money(Math.addExact(this.amount, money.amount()), this.currency);
    }

    public Money subtract(Money money) {
        checkSameCurrency(money);
        return new Money(Math.subtractExact(this.amount, money.amount()), this.currency);
    }

    public Money multiply(long multiplier) {
        return new Money(Math.multiplyExact(this.amount, multiplier), this.currency);
    }

    public boolean isZero() {
        return this.amount == 0;
    }

    public boolean isPositive() {
        return this.amount > 0;
    }

    public boolean isNegative() {
        return this.amount < 0;
    }

    public boolean isGreaterThan(Money other) {
        return this.compareTo(other) > 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return this.compareTo(other) >= 0;
    }

    public boolean isLessThan(Money other) {
        return this.compareTo(other) < 0;
    }

    public boolean isLessThanOrEqual(Money other) {
        return this.compareTo(other) <= 0;
    }

    private void checkSameCurrency(Money money) {
        if (!this.currency.equals(money.currency())) {
            throw new IllegalArgumentException("CURRENCY_MISMATCH");
        }
    }

    @Override
    public int compareTo(Money other) {
        checkSameCurrency(other);
        return Long.compare(this.amount, other.amount());
    }
}
