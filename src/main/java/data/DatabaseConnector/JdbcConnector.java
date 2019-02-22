package data.DatabaseConnector;

import managers.SecretData;

import java.sql.*;

public class JdbcConnector {
    private static String dbUtl = SecretData.dbUtl;
    private static String dbLogin = SecretData.dbLogin;
    private static String dbPass = SecretData.dbPass;

    public static void JdbcConnector() {
        try {
            Connection con = DriverManager.getConnection(dbUtl, dbLogin, dbPass);
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
