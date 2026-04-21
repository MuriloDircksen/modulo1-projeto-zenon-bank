package br.com.zenon.fraud.infrastructure;

import br.com.zenon.fraud.domain.model.Transaction;
import br.com.zenon.fraud.infrastructure.Interfaces.TransactionRepository;

import java.util.List;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {

    private final List<Transaction> _transactions;

    public TransactionListRepository(List<Transaction> transactions) {
        _transactions = transactions;
    }
    @Override
    public Optional<Transaction> GetTransactionByCustomerName(String customerName) {

        return _transactions.stream()
                .filter(trans -> trans.customerOrig().name().equals(customerName))
                .findFirst();
    }

    @Override
    public void save(Transaction transaction) {

    }
}
