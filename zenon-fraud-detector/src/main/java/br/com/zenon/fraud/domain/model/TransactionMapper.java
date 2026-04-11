package br.com.zenon.fraud.domain.model;

import java.math.BigDecimal;

public class TransactionMapper {
    public Transaction ToDomain(
            String stepStr,
            String typeStr,
            String amountStr,
            String nameOrigStr,
            String oldbalanceOrgStr,
            String newbalanceOrigStr,
            String nameDestStr,
            String oldbalanceDestStr,
            String newbalanceDestStr,
            String isFraudStr,
            String isFlaggedFraudStr
    ) {
        CustomerTransaction customerTransactionOrig = new CustomerTransaction
                    (nameOrigStr, new BigDecimal(oldbalanceOrgStr), new BigDecimal(newbalanceOrigStr));

        CustomerTransaction customerTransactionDest = new CustomerTransaction
                    (nameDestStr, new BigDecimal(oldbalanceDestStr), new BigDecimal(newbalanceDestStr));

        return new Transaction(Integer.parseInt(stepStr), TransactionType.valueOf(typeStr.toUpperCase()), new BigDecimal(amountStr),
                customerTransactionOrig, customerTransactionDest,
                isFraudStr.equals("1") || Boolean.parseBoolean(isFraudStr),
                isFlaggedFraudStr.equals("1") || Boolean.parseBoolean(isFlaggedFraudStr));
    }
}
