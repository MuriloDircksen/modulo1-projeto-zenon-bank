package br.com.zenon.fraud.domain.model;

import java.math.BigDecimal;

public record Transaction (int step, TransactionType type, BigDecimal amount, CustomerTransaction customerOrig, CustomerTransaction customerDest,
                           boolean isFraud, boolean isFlaggedFraud) {
    public Transaction {
        if (step <= 0) {
            throw new IllegalArgumentException("step should be positive: " + step);
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount should be positive: " + amount);
        }
    }
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

    public void validateCustomerTransaction(String context, CustomerTransaction customerTransaction) {

    }
}
