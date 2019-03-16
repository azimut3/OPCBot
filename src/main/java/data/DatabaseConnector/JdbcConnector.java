package data.DatabaseConnector;

import data.Subscriprions.Subs;
import managers.Admin;
import org.telegram.telegrambots.api.objects.Update;

import java.sql.*;
import java.util.ArrayList;

public class JdbcConnector {

    private static Connection connection;

    public JdbcConnector() {
        try {
            String dbUrl = "jdbc:postgresql://ec2-79-125-6-250.eu-west-1.compute.amazonaws.com:5432" +
                    "/d988nf711pgp3d?user=usxauwamyzthkf&" +
                    "password=c453e9900374256011124e409486b3674edc8687cf880977e7c2f3580be7436b&" +
                    "sslmode=require";
            //String dbUrl = System.getenv("JDBC_DATABASE_URL");
            connection = DriverManager.getConnection(dbUrl);
            initTable();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private Statement initTable() {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery("select * from users;");
            while (result.next()){
                ArrayList<String> user = new ArrayList<>();
                user.add(result.getString("weather_sub"));
                user.add(result.getString("selected_berths"));
                user.add(result.getString("berth_sub"));
                user.add(result.getString("berth_update"));
                user.add(result.getString("status"));
                user.add(result.getString("user_info"));
                Subs.users.put(result.getString("chat_id"), user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static void wipeData() {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            statement.executeUpdate("DELETE FROM users *;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String chatId, String weatherUpdate, String berthSequence,
                                    String berthSub, String berthUpdate, String status,
                                    String userInfo, String name, String surname){
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            statement.executeUpdate("INSERT INTO users VALUES(" + chatId + ", " + weatherUpdate +
                            ", " + berthSequence + ", "+ berthSub + ", " + berthUpdate + ", " + status
                    + ", " + userInfo +  ", '" + name+ "', '" + surname +"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addBasicUser(Update update) {
        String userId = String.valueOf(update.getMessage().getChatId());
        if (!Subs.users.containsKey(userId)) {
            String name = update.getMessage().getChat().getFirstName();
            String surname = update.getMessage().getChat().getLastName();
            ArrayList<String> user = new ArrayList<>();
            user.add("'false'");
            user.add("'0'");
            user.add("'false'");
            user.add("'false'");
            user.add("'user'");
            user.add("'" + Subs.getBasicDateInfo() + "'");
            addUser(userId, user.get(0), user.get(1), user.get(2),
                    user.get(3), user.get(4), user.get(5), name,
                    surname);
            System.out.println(user.get(5));
            Subs.users.put(userId, user);
            Admin.newUser(userId, name, surname);
        }
    }

    public static void editWeatherSub(String chatId, String value){
        editUser(chatId, "weather_sub", value);
    }

    public static void editSelectedBerths(String chatId, String value){
        editUser(chatId, "selected_berths", value);
    }

    public static void editBerthSub(String chatId, String value){
        editUser(chatId, "berth_sub", value);
    }

    public static void editBerthUpdate(String chatId, String value){
        editUser(chatId, "berth_update", value);
    }

    public static void editUser(String chatId, String colName, String colValue) {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            statement.executeUpdate("update users set " + colName + "='" + colValue +
                    "' where chat_id='" + chatId + "' ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCalls(String chatId){
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            statement.executeUpdate("update users set calls = calls + 1" +
                    " where chat_id='" + chatId + "' ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resetCalls(){
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            statement.executeUpdate("update users set calls = 0");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getUsersAndAvgCalls(){
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            statement.executeQuery("select count(chat_id), avg(calls)users from users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] stats = statement.toString().split(" ");
        return stats[0] + " (" + Math.round(Double.parseDouble(stats[1])*100)/100 + ")";
    }

    public static Connection getConnection() {
        return connection;
    }
}
