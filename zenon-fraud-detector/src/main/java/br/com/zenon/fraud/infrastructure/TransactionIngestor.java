package br.com.zenon.fraud.infrastructure;

import br.com.zenon.fraud.domain.model.Transaction;
import br.com.zenon.fraud.domain.model.TransactionMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionIngestor {
    public List<Transaction> ToTransactionList(String pathName) {
        List transactionList = new ArrayList<Transaction>();

        TransactionMapper transactionMapper = new TransactionMapper();
        try (BufferedReader br = new BufferedReader(new FileReader(pathName))) {
            String line = br.readLine();

            int contador = 0;
            while ((line = br.readLine()) != null && contador < 1000) {
                try {
                    String[] v = line.split(",");
                    transactionList.add(transactionMapper.ToDomain
                            (v[0], v[1], v[2], v[3], v[4], v[5], v[6], v[7], v[8], v[9], v[10]));
                }
                catch (IllegalArgumentException e) {
                    System.err.println("Erro: " + line +" | "+ e.toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transactionList;
    }
}
