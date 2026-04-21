package br.com.zenon.fraud.infrastructure;

import br.com.zenon.fraud.domain.model.Transaction;
import br.com.zenon.fraud.domain.model.TransactionMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionIngestor {
    public static final int FRAUD_LIMIT = 10000;

    public List<Transaction> ToTransactionList(String pathName) {
        List<Transaction> transactionList = new ArrayList<>();

        TransactionMapper transactionMapper = new TransactionMapper();
        try (BufferedReader br = new BufferedReader(new FileReader(pathName))) {
            String line = br.readLine();

            int contador = 0;
            while ((line = br.readLine()) != null && contador < FRAUD_LIMIT) {
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

    public List<Transaction> ToTransactionList2(String pathName) {
        Path path = Path.of(pathName);
        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    .limit(FRAUD_LIMIT)
                    .map(this::parseTransaction)
                    //.flatMap(Optional::stream)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private Optional<Transaction> parseTransaction(String line) {
        try {
            String[] chunks = line.split(",");
            Transaction transaction = new TransactionMapper().ToDomain(
                    chunks[0], chunks[1], chunks[2], chunks[3], chunks[4],
                    chunks[5], chunks[6], chunks[7], chunks[8], chunks[9], chunks[10]
            );
            return Optional.of(transaction);
        }catch (IllegalArgumentException e) {
            System.err.println("Erro: " + line +" | "+ e.toString());
            return Optional.empty();
        }
    }

    public Map<String, Transaction> ToTransactionMap(String pathName) {
        Path path = Path.of(pathName);
        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    //.limit(FRAUD_LIMIT)
                    .map(this::parseTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toMap(
                            t -> t.customerOrig().name(),
                            t -> t));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
