package br.com.zenon.fraud.infrastructure;

import br.com.zenon.fraud.domain.model.CustomerTransaction;
import br.com.zenon.fraud.domain.model.Transaction;
import br.com.zenon.fraud.domain.model.TransactionType;
import br.com.zenon.fraud.infrastructure.Interfaces.TransactionRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class TransactionSQLRepository implements TransactionRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/transaction";
    private static final String USER = "root";
    private static final String PASSWORD = "senha123";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public Optional<Transaction> GetTransactionByCustomerName(String customerName) {
        String sql = """
            SELECT * FROM Transactions 
            WHERE nameOrig = ?
            ORDER BY step
            LIMIT 1
            """;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customerName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToItem(rs));
                }
            }
        }
        catch (SQLException e) {
        throw new RuntimeException(e);
        }
        return Optional.empty();
    }
    @Override
    public void save(Transaction transaction) {
        String sql = """
            INSERT INTO 
                Transactions (step, type, amount, nameOrig, oldBalanceOrig, newBalanceOrig, nameDest, oldBalanceDest, newBalanceDest, isFraud, isFlaggedFraud) 
            VALUES 
                (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, transaction.step());
            ps.setString(2, transaction.type().name());
            ps.setBigDecimal(3, transaction.amount());
            ps.setString(4, transaction.customerOrig().name());
            ps.setBigDecimal(5, transaction.customerOrig().oldBalance());
            ps.setBigDecimal(6, transaction.customerOrig().newBalance());
            ps.setString(7, transaction.customerDest().name());
            ps.setBigDecimal(8, transaction.customerDest().oldBalance());
            ps.setBigDecimal(9, transaction.customerDest().newBalance());
            ps.setBoolean(10, transaction.isFraud());
            ps.setBoolean(11, transaction.isFlaggedFraud());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Transaction mapResultSetToItem(ResultSet rs) throws SQLException {

        int step = rs.getInt("step");
        TransactionType type = TransactionType.valueOf(rs.getString("type").toUpperCase());
        BigDecimal amount = rs.getBigDecimal("amount");
        String nameOrig = rs.getString("nameOrig");
        BigDecimal oldBalanceOrig = rs.getBigDecimal("oldBalanceOrig");
        BigDecimal newBalanceOrig = rs.getBigDecimal("newBalanceOrig");
        String nameDest = rs.getString("nameDest");
        BigDecimal oldBalanceDest = rs.getBigDecimal("oldBalanceDest");
        BigDecimal newBalanceDest = rs.getBigDecimal("newBalanceDest");
        boolean isFraud = rs.getBoolean("isFraud");
        boolean isFlaggedFraud = rs.getBoolean("isFlaggedFraud");

        CustomerTransaction customerTransactionOrig = new CustomerTransaction
                (nameOrig, oldBalanceOrig, newBalanceOrig);

        CustomerTransaction customerTransactionDest = new CustomerTransaction
                (nameDest, oldBalanceDest, newBalanceDest);

        return new Transaction(step, type,amount, customerTransactionOrig, customerTransactionDest,
                isFraud,isFlaggedFraud);
    }
}
