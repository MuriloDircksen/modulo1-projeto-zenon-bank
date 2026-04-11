package br.com.zenon.fraud.infrastructure;

import br.com.zenon.fraud.domain.model.Transaction;
import br.com.zenon.fraud.domain.model.TransactionType;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FraudAnalyzer {
    public long GetFraudCount(List<Transaction> transactionList) {
        return transactionList.stream()
                .filter(Transaction::isFraud)
                .count();
    }
    public List<BigDecimal> GetTop3BigFraud(List<Transaction> transactionList) {
        return transactionList.stream()
                .filter(Transaction::isFraud)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .limit(3)
                .map(Transaction::amount)
                .toList();
    }
    public Set<String> GetSuspectCustomers(List<Transaction> transactionList) {
        return transactionList.stream()
                .filter(Transaction::isFraud)
                .map(transaction -> transaction.customerOrig().name())
                .collect(Collectors.toSet());
    }
    public BigDecimal GetTotalLoss(List<Transaction> transactionList) {
        return transactionList.stream()
                .filter(Transaction::isFraud)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public Map<TransactionType, Long> GetFraudCountByType(List<Transaction> transactionList) {
        return transactionList.stream()
                .filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(
                        Transaction::type,
                        Collectors.counting()
                ));
    }
}
