package br.com.zenon;

import br.com.zenon.fraud.infrastructure.TransactionIngestor;
import br.com.zenon.fraud.infrastructure.TransactionSQLRepository;

public class DBMain {
    void main() {
        /*
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        var transactions = transactionIngestor.ToTransactionList2("data/archive/PS_20174392719_1491204439457_log.csv");

        TransactionSQLRepository transactionSQLRepository = new TransactionSQLRepository();

        var time1 = System.currentTimeMillis();
        transactions.forEach(transactionSQLRepository::save);
        var time2 = System.currentTimeMillis();
        IO.println(time2-time1);
        */
        TransactionSQLRepository transactionSQLRepository = new TransactionSQLRepository();
        var result = transactionSQLRepository.GetTransactionByCustomerName("C1231006815");
        result.ifPresent(IO::println);
        var result2 = transactionSQLRepository.GetTransactionByCustomerName("C12345");
        result2.ifPresentOrElse(
                IO::println,
                () -> IO.println("Transação não encontrada para o cliente " + "C12345")
        );
    }
}
