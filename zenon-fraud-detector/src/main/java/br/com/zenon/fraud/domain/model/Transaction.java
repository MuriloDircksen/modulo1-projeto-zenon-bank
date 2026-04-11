package br.com.zenon.fraud.domain.model;

import java.math.BigDecimal;

public record Transaction (int step, TransactionType type, BigDecimal amount, CustomerTransaction customerOrig, CustomerTransaction customerDest,
                           boolean isFraud, boolean isFlaggedFraud) {
    @Override
    public String toString() {
        return "Transaction{" +
                "step=" + step +
                ", type=" + type +
                ", amount=" + amount +
                ", customerOrig=" + customerOrig +
                ", customerDest=" + customerDest +
                ", isFraud=" + isFraud +
                ", isFlaggedFraud=" + isFlaggedFraud +
                '}';
    }
}
