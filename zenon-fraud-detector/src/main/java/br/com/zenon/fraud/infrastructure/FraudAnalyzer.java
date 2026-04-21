package br.com.zenon.fraud.infrastructure;

import br.com.zenon.fraud.domain.model.Transaction;
import br.com.zenon.fraud.domain.model.TransactionType;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FraudAnalyzer {
    public long GetFraudCount(List<Transaction> transactionList) {
        return getTransactionStream(transactionList)
                .count();
    }
    public List<BigDecimal> GetTopBigFraud(List<Transaction> transactionList, long size) {
        return getTransactionStream(transactionList)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .limit(size)
                .map(Transaction::amount)
                .toList();
    }

    private static Stream<Transaction> getTransactionStream(List<Transaction> transactionList) {
        return transactionList.stream()
                .filter(Transaction::isFraud);
    }

    public List<String> GetSuspectCustomers(List<Transaction> transactionList, long size) {
        return getTransactionStream(transactionList)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .map(transaction -> transaction.customerOrig().name())
                .distinct()
                .limit(size)
                .toList();
    }
    public BigDecimal GetTotalLoss(List<Transaction> transactionList) {
        return getTransactionStream(transactionList)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public Map<TransactionType, Long> GetFraudCountByType(List<Transaction> transactionList) {
        return getTransactionStream(transactionList)
                .collect(Collectors.groupingBy(
                        Transaction::type,
                        Collectors.counting()
                ));
    }
}
