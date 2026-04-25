package br.com.zenon;

import br.com.zenon.fraud.infrastructure.EfficientTransactionIngestor;
import br.com.zenon.fraud.infrastructure.TransactionIngestor;
import br.com.zenon.fraud.infrastructure.TransactionSQLRepository;

public class IngestionMain {
    void main() {
        var transactionSQLRepository = new TransactionSQLRepository();

        var ingestor = new EfficientTransactionIngestor();
        long starTimeSQL = System.nanoTime();
        IO.println("Iniciando adição dados no DB");
        ingestor.readAsBatch("data/archive/PS_20174392719_1491204439457_log.csv",
                transactionSQLRepository::saveAll);

        long endTimeSQL = System.nanoTime();

        IO.println(("Tempo de ingestão - SQL(ms): " + ((endTimeSQL-starTimeSQL)/1_000_000.0)));
    }
}
