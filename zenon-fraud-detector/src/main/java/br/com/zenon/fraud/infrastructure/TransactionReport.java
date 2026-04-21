package br.com.zenon.fraud.infrastructure;

import br.com.zenon.fraud.domain.model.Transaction;
import br.com.zenon.fraud.domain.model.TransactionMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class TransactionReport {
    private record ReportTransaction(BigDecimal amount, boolean isFraud){}

    public record Statics(long totalTransaction, long totalFruad, BigDecimal totalAmount){
        private static final Statics Zero = new Statics(0, 0, BigDecimal.ZERO);

        private Statics addReport(ReportTransaction rt) {
            return new Statics(
                    totalTransaction + 1,
                    totalFruad + (rt.isFraud ? 1: 0),
                    totalAmount.add(rt.amount));
        }

        private Statics add(Statics other) {
            return new Statics(totalTransaction + other.totalTransaction,
                    totalFruad + other.totalFruad, totalAmount.add(other.totalAmount));
        }
    };


    public void GenerateReport (String path, Locale locale) {
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
            ResourceBundle bundle = ResourceBundle.getBundle("message", locale);
            IO.println(bundle.getString("totalLines") + ": " + countLines);
            IO.println(bundle.getString("totalFrauds") + ": " + countFraud);
            IO.println(bundle.getString("totalAmount") + ": %2f".formatted(totalAmount[0]));
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



    public Statics GenerateReport2 (String path, Locale locale) {
        try(Stream<String> lines = Files.lines(Path.of(path))) {

            var result = lines
                    .skip(1)
                    .map(TransactionReport::parseReportTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .reduce(
                            Statics.Zero,
                            Statics::addReport,
                            Statics::add
                    );

            ResourceBundle bundle = ResourceBundle.getBundle("message", locale);
            IO.println(bundle.getString("totalLines") + ": " + result.totalTransaction());
            IO.println(bundle.getString("totalFrauds") + ": " + result.totalFruad);
            IO.println(bundle.getString("totalAmount") + ": %2f".formatted(result.totalAmount));
            return result;
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }
    private static Optional<ReportTransaction> parseReportTransaction(String line) {
        try {
            String[] chunks = line.split(",");

            return Optional.of(new ReportTransaction(
                    new BigDecimal(chunks[2]), chunks[9].equals("1")
            ));
        }catch (IllegalArgumentException e) {
            System.err.println("Erro: " + line +" | "+ e.toString());
            return Optional.empty();
        }
    }
}
