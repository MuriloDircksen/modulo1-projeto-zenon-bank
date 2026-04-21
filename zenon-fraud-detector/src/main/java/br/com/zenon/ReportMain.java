package br.com.zenon;

import br.com.zenon.fraud.infrastructure.TransactionReport;

import java.util.Locale;

public class ReportMain {
    void main() {
        TransactionReport transactionReport = new TransactionReport();
        transactionReport.GenerateReport2("data/archive/PS_20174392719_1491204439457_log.csv", Locale.of("pt", "BR"));
    }
}
