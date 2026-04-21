package br.com.zenon.fraud.infrastructure.Interfaces;

import br.com.zenon.fraud.domain.model.Transaction;

import java.util.Optional;

public interface TransactionRepository {
    Optional<Transaction> GetTransactionByCustomerName(String customerName);
    void save(Transaction transaction);
}
