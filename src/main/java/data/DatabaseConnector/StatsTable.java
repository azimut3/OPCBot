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

    public static String getMonthStats(){
        StringBuilder builder = new StringBuilder();
        builder.append("Статистика за месяц:").append(System.lineSeparator());
        Statement statement;
        try(Connection connection = Connector.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select users, weather_subs, berth_subs," +
                            " berth_updates, active, calls, date, month, year from stats where month = '" +
                            UkrCalendar.getFullDate().substring(3,5) + "'");
            while (!resultSet.isLast()){
                resultSet.next();
                builder.append(resultSet.getString("active")).append("/")
                        .append(resultSet.getString("users"))
                        .append(" ~").append(resultSet.getString("calls")).append(" [")
                        .append(resultSet.getString("weather_subs")).append("|")
                        .append(resultSet.getString("berth_subs")).append("|")
                        .append(resultSet.getString("berth_updates")).append("] ")
                        .append(resultSet.getString("date")).append(".")
                        .append(resultSet.getString("month")).append(".")
                        .append(resultSet.getString("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    enum Table{

    }
}
