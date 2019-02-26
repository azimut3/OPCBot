package data.DatabaseConnector;

import java.sql.*;

public class JdbcConnector {

    private Connection connection;

    public JdbcConnector() {
        try {
            String dbUrl = "jdbc:postgresql://ec2-79-125-6-250.eu-west-1.compute.amazonaws.com:5432/d988nf711pgp3d?user=usxauwamyzthkf&password=c453e9900374256011124e409486b3674edc8687cf880977e7c2f3580be7436b&sslmode=require";
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private Statement initTable() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }
}
