package data.DatabaseConnector;

import managers.UkrCalendar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatsTable {
    private static StatsTable statsTableInstance;

    public StatsTable() {
    }

    public static void recordStats(){
        //Connection connection = Connector.getConnection();
        Statement statement;
        ResultSet result = UserTableConnector.getUsersStats();
        String[] date = UkrCalendar.getFullDate().split("\\.");
        try(Connection connection = Connector.getConnection()) {
            String users = result.getString("users");
            String weather_subs = result.getString("weather_subs");
            String berth_subs = result.getString("berth_subs");
            String berth_updates = result.getString("berth_updates");
            String active = result.getString("active");
            String calls = result.getString("calls");
            String day = date[0];
            String month = date[1];
            String year = date[2];
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO stats (users, weather_subs, berth_subs," +
                    " berth_updates, active, calls, date, month, year)" +
                    "values('" + users +"', '" + weather_subs + "', '" + berth_subs + "', '" +
                    berth_updates + "', '" + active + "', '" + calls + "', '" +
                    day + "', '" + month + "', '" + year + "')" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getMonthSTats(){
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

}
