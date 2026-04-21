package br.com.zenon.fraud.infrastructure;

import br.com.zenon.fraud.domain.model.Transaction;
import br.com.zenon.fraud.infrastructure.Interfaces.TransactionRepository;

import java.util.Map;
import java.util.Optional;

public class TransactionMapRepository implements TransactionRepository {

    private final Map<String, Transaction> _transactions;

    public TransactionMapRepository(Map<String, Transaction> transactions) {
        _transactions = transactions;
    }

    public Optional<Transaction> GetTransactionByCustomerName(String customerName) {
        return Optional.ofNullable(_transactions.get(customerName));
    }

    @Override
    public void save(Transaction transaction) {

    }
}
