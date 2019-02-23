package data.DatabaseConnector;

import managers.SecretData;

import java.sql.*;

public class JdbcConnector {
    private static String dbUtl = SecretData.dbUtl;
    private static String dbLogin = SecretData.dbLogin;
    private static String dbPass = SecretData.dbPass;

    private Statement statement;

    public JdbcConnector() {
        try {
            Connection con = DriverManager.getConnection(dbUtl, dbLogin, dbPass);
            statement = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
