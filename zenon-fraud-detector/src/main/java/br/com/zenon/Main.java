package br.com.zenon;

import br.com.zenon.fraud.domain.model.Transaction;
import br.com.zenon.fraud.domain.model.TransactionMapper;
import br.com.zenon.fraud.infrastructure.FraudAnalyzer;
import br.com.zenon.fraud.infrastructure.TransactionIngestor;

import java.util.List;

public class Main {
    void main() {
        TransactionMapper transactionMapper = new TransactionMapper();
        /*
        Transaction transaction1 = transactionMapper.ToDomain("1", "PAYMENT", "9839.64", "C1231006815", "170136.0",
                "160296.36", "M1979787155", "0.0", "0.0", "0", "0");

        Transaction transaction2 = transactionMapper.ToDomain("743", "CASH_OUT", "850002.52", "C1280323807", "850002.52",
                "0.0", " C873221189", "6510099.11", "7360101.63", "1", "0");
        IO.println(transaction1);
        IO.println(transaction2);
        */
        /*
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions = transactionIngestor.ToTransactionList("data/archive/PS_20174392719_1491204439457_log.csv");

        for(int i = 0; i < 10; i++) {
            IO.println(transactions.get(i));
        }
        */
        /*
        TransactionIngestor transactionIngestor2 = new TransactionIngestor();
        List<Transaction> transactions2 = transactionIngestor2.ToTransactionList("data/archive/paysim_with_bad_data.csv.txt");

        transactions2.forEach(IO::println);
         */
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions = transactionIngestor.ToTransactionList2("data/archive/PS_20174392719_1491204439457_log.csv");

        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer();

        IO.println("1. Total de Fraudes: " + fraudAnalyzer.GetFraudCount(transactions));
        IO.println("\n2. Top 3 Fraudes de Maior Valor: ");
        fraudAnalyzer.GetTopBigFraud(transactions, 3).forEach(amount -> IO.println("- %2f".formatted(amount)));
        IO.println("\n3. Clientes Suspeitos: ");
        fraudAnalyzer.GetSuspectCustomers(transactions, 5).forEach(IO::println);
        IO.println("4. Prejuízo Total: " + fraudAnalyzer.GetTotalLoss(transactions));
        IO.println("\n5. Fraudes por Tipo: ");
        fraudAnalyzer.GetFraudCountByType(transactions)
                .forEach((type, count) -> IO.println(type + ": " + count));


    }
}
