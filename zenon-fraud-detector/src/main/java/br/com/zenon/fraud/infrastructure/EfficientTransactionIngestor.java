package br.com.zenon.fraud.infrastructure;

import br.com.zenon.fraud.domain.model.Transaction;
import br.com.zenon.fraud.domain.model.TransactionMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EfficientTransactionIngestor {
    public static final int FRAUD_LIMIT = 10_000;
    public static final int LINE_BATCH_SIZE = 2_500;

    public void readAsStream(String pathName, Consumer<Transaction> consumer) {
        try(Stream<String> lines = Files.lines(Path.of(pathName))) {
            lines
                    .skip(1)
                    .limit(FRAUD_LIMIT)
                    .map(this::parseTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(consumer);

        }
        catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    public void readAsBatch(String pathName, Consumer<List<Transaction>> batchConsumer) {
        try(ExecutorService executor= Executors.newFixedThreadPool(10);
            Stream<String> lines = Files.lines(Path.of(pathName)).skip(1);) {

            var iterator = lines.iterator();

            List<String> lineBatch = new ArrayList<>(LINE_BATCH_SIZE);
            while (iterator.hasNext()) {
                String line = iterator.next();
                lineBatch.add(line);

                if(lineBatch.size() >= LINE_BATCH_SIZE) {
                    //evitar que as threads se choquem no acesso e limpeza da lista
                    final List<String> currentLineBatch = List.copyOf(lineBatch);
                    executor.submit(() -> executeBatch(currentLineBatch, batchConsumer));
                    lineBatch.clear();
                }

            }
            if (!lineBatch.isEmpty()) {
                final List<String> currentLineBatch = List.copyOf(lineBatch);
                executor.submit(() -> executeBatch(currentLineBatch, batchConsumer));
            }
        }
        catch (IOException ex) {
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
    public void executeBatch(List<String> lineBatch, Consumer<List<Transaction>> batchConsumer) {
        List<Transaction> transactions = lineBatch
                .stream()
                .map(this::parseTransaction)
                .filter((Optional::isPresent))
                .map(Optional::get)
                .toList();
        batchConsumer.accept(transactions);
    }
}
