package br.com.zenon.fraud.infrastructure;

import br.com.zenon.fraud.domain.model.Transaction;
import br.com.zenon.fraud.domain.model.TransactionMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class TransactionReport {

    public void GenerateReport (String path) {
        try(Stream<String> lines = Files.lines(Path.of(path))) {

            AtomicLong countLines = new AtomicLong();
            AtomicLong countFraud = new AtomicLong();
            final BigDecimal[] totalAmount = {BigDecimal.ZERO};

            lines.skip(1)
                    .forEach(line -> {
                        Optional<Transaction> parsedtransaction = parseTransaction(line);
                        countLines.getAndIncrement();

                        if (parsedtransaction.isPresent()) {
                            Transaction transaction = parsedtransaction.get();
                            if (transaction.isFraud()) {
                                countFraud.getAndIncrement();
                            }
                            totalAmount[0] = totalAmount[0].add(transaction.amount());
                        }
                    });
            IO.println("Total de linhas: " + countLines);
            IO.println("Total de fraudes " + countFraud);
            IO.println("Valor total transacionado: %2f".formatted(totalAmount[0]));
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public List<Transaction> GetAllTransactions(String path) {
        try(Stream<String> lines = Files.lines(Path.of(path))) {
            return lines
                    .skip(1)
                    .map(this::parseTransaction)
                    .filter(Optional::isPresent)
                    .flatMap(Optional::stream)
                    .toList();
        }
        catch (IOException e) {
            throw new RuntimeException();
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
}
