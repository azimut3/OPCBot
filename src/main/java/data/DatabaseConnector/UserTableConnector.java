package data.DatabaseConnector;

import data.Subscriprions.Subs;
import data.Subscriprions.User;
import managers.Admin;
import org.telegram.telegrambots.api.objects.Update;

import java.sql.*;

public class UserTableConnector {

    public UserTableConnector() {
        initTable();
    }

    private Statement initTable() {
        Statement statement = null;
        try {
            Connection connection = Connector.getConnection();
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from users;");
            while (result.next()){
                String userChatId = result.getString("chat_id");
                String weatherSubscription = result.getString("weather_sub");
                String berthsSelected = result.getString("selected_berths");
                String berthStatusSubscription = result.getString("berth_sub");
                String berthUpdateSubscription = result.getString("berth_update");
                String status = result.getString("status");
                String info = result.getString("user_info");
                Subs.users.put(userChatId, new User(userChatId, weatherSubscription, berthsSelected,
                        berthStatusSubscription, berthUpdateSubscription, status, info));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static void wipeData() {
        Statement statement = null;
        try {
            Connection connection = Connector.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM users *;");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String chatId, String weatherUpdate, String berthSequence,
                                    String berthSub, String berthUpdate, String status,
                                    String userInfo, String name, String surname){
        Statement statement = null;
        try {
            Connection connection = Connector.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO users VALUES(" + chatId + ", " + weatherUpdate +
                            ", " + berthSequence + ", "+ berthSub + ", " + berthUpdate + ", " + status
                    + ", " + userInfo +  ", '" + name+ "', '" + surname +"');");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addBasicUser(Update update) {
        String userChatId = String.valueOf(update.getMessage().getChatId());
        if (!Subs.users.containsKey(userChatId)) {
            String name = update.getMessage().getChat().getFirstName();
            String surname = update.getMessage().getChat().getLastName();
            String weatherSubscription = "'false'";
            String berthsSelected = "'0'";
            String berthStatusSubscription = "'false'";
            String berthUpdateSubscription = "'false'";
            String status = "'user'";
            String info = "'" + Subs.getBasicDateInfo() + "'";

            addUser(userChatId, weatherSubscription, berthsSelected,
                    berthStatusSubscription, berthUpdateSubscription, status, info, name,
                    surname);
            Subs.users.put(userChatId, new User(userChatId, info));
            Admin.newUser(userChatId, name, surname);
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
            Connection connection = Connector.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("update users set " + colName + "='" + colValue +
                    "' where chat_id='" + chatId + "' ;");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCalls(String chatId){
        Statement statement = null;
        try {
            Connection connection = Connector.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("update users set calls = calls + 1" +
                    " where chat_id='" + chatId + "' ;");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resetCalls(){
        Statement statement = null;
        try {
            Connection connection = Connector.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("update users set calls = 0");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getUsersAndAvgCalls(){
        Statement statement = null;
        String res = null;
        try {
            Connection connection = Connector.getConnection();
            statement = connection.createStatement();
            ResultSet result = statement
                    .executeQuery("select count(chat_id) users, COALESCE(avg(calls), 0) calls from users " +
                            "where calls>0 and status!='admin'");
            result.next();
            System.out.println(result.getString("calls"));
            res = result.getString("users") +
                    " (" + Math.round(Double.parseDouble(
                            result.getString("calls")) * 100) / 100 + ")";
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;

    }

    public static ResultSet getUsersStats(){
        Statement statement;
        ResultSet result = null;
        try {
            Connection connection = Connector.getConnection();
            statement = connection.createStatement();
            result = statement
                    .executeQuery("select users, weather_subs, berth_subs,  berth_updates," +
                            " active, B.calls as calls " +
                            "from " +
                            "(select count(chat_id) users, count(weather_sub) weather_subs, " +
                            "count(berth_sub) berth_subs, count(berth_update) berth_updates " +
                            "from users where status!='admin') A, " +
                            "(select count(chat_id) active, COALESCE(avg(calls), 0) calls from users " +
                            "where calls>0 and status!='admin') B");
            result.next();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }
}
