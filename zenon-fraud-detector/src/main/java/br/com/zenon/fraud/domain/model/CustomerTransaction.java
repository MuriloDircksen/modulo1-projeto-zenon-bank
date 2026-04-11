package br.com.zenon.fraud.domain.model;

import java.math.BigDecimal;

public record CustomerTransaction (String name, BigDecimal oldbalance, BigDecimal newbalance) {

    public CustomerTransaction {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("name should not be empty");
        }
        if (newbalance == null || newbalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("newBalance should be positive:" + newbalance);
        }
        if (oldbalance == null || oldbalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("oldBalance should be positive: " + oldbalance);
        }
    }

    @Override
    public String toString() {
        return "CustomerTransaction{" +
                "name='" + name + '\'' +
                ", oldbalance=" + oldbalance +
                ", newbalance=" + newbalance +
                '}';
    }
}
