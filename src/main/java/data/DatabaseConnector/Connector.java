package data.DatabaseConnector;

import managers.SecretData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static volatile Connection connection;

    public static synchronized Connection getConnection() {
            try {
                //String dbUrl = SecretData.jdbcData;
                String dbUrl = System.getenv("JDBC_DATABASE_URL");
                connection = DriverManager.getConnection(dbUrl);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        return connection;
    }
}
