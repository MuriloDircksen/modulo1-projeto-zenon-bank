package br.com.zenon.fraud.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public record CustomerTransaction (String name, BigDecimal oldBalance, BigDecimal newBalance) {

    public CustomerTransaction {
        Objects.requireNonNull(name);
        Objects.requireNonNull(oldBalance);
        Objects.requireNonNull(newBalance);
        if(name.isBlank()) {
            throw new IllegalArgumentException("name should not be empty");
        }
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("newBalance should be positive:" + newBalance);
        }
        if (oldBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("oldBalance should be positive: " + oldBalance);
        }
    }

    @Override
    public String toString() {
        return "CustomerTransaction{" +
                "name='" + name + '\'' +
                ", oldbalance=" + oldBalance +
                ", newbalance=" + newBalance +
                '}';
    }
}
