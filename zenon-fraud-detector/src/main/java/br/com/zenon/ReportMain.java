package br.com.zenon;

import br.com.zenon.fraud.infrastructure.TransactionReport;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportMain {
    void main(String[] args) {
        String language = (args.length > 0 ? args[0] : "pt");
        TransactionReport transactionReport = new TransactionReport();
        var statics = transactionReport.GenerateReport2("data/archive/PS_20174392719_1491204439457_log.csv", Locale.of("pt", "BR"));
        var locale = Locale.of(language);

        var integerFormatter = NumberFormat.getIntegerInstance(locale);
        var currencyFormatter = DecimalFormat.getCurrencyInstance(locale);

        currencyFormatter.setCurrency(Currency.getInstance("USD"));

        String formattedTotalTransactions = integerFormatter.format(statics.totalTransaction());
        String formattedTotalFrauds = integerFormatter.format(statics.totalFruad());
        String formattedTotalAmount = currencyFormatter.format((statics.totalAmount()));

        ResourceBundle bundle = ResourceBundle.getBundle("message", locale);

        IO.println(bundle.getString("totalLines") + ": " + formattedTotalTransactions);
        IO.println(bundle.getString("totalFrauds") + ": " + formattedTotalFrauds);
        IO.println(bundle.getString("totalAmount") + ": " + formattedTotalAmount);
    }
}
