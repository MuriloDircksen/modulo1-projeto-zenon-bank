package br.com.zenon.fraud.domain.model;

import java.math.BigDecimal;

public record CustomerTransaction (String name, BigDecimal oldbalance, BigDecimal newbalance) {
    @Override
    public String toString() {
        return "CustomerTransaction{" +
                "name='" + name + '\'' +
                ", oldbalance=" + oldbalance +
                ", newbalance=" + newbalance +
                '}';
    }
}
