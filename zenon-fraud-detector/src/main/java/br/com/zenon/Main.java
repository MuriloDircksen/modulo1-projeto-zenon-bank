package br.com.zenon;

import br.com.zenon.fraud.domain.model.Transaction;
import br.com.zenon.fraud.domain.model.TransactionMapper;
import br.com.zenon.fraud.domain.model.TransactionType;
import br.com.zenon.infrastructure.TransactionIngestor;

import java.util.List;

public class Main {
    void main() {
        TransactionMapper transactionMapper = new TransactionMapper();
        
        Transaction transaction1 = transactionMapper.ToDomain("1", "PAYMENT", "9839.64", "C1231006815", "170136.0",
                "160296.36", "M1979787155", "0.0", "0.0", "0", "0");

        Transaction transaction2 = transactionMapper.ToDomain("743", "CASH_OUT", "850002.52", "C1280323807", "850002.52",
                "0.0", " C873221189", "6510099.11", "7360101.63", "1", "0");
        IO.println(transaction1);
        IO.println(transaction2);
        
    }
}
