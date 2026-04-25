package br.com.zenon.fraud.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/transaction";
    private static final String USER = "root";
    private static final String PASSWORD = "senha123";

    private ConnectionFactory() {}

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
