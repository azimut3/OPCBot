package Subscriptions;

import data.DatabaseConnector.UserTableConnector;
import data.Subscriprions.Subs;
import managers.UkrCalendar;

public class AdminTest {
    public static void main(String ... args){
        new UserTableConnector();
        String usersActiveStats = UserTableConnector.getUsersAndAvgCalls();
        System.out.println(usersActiveStats);
        StringBuilder builder = new StringBuilder();
        builder.append(Subs.users.size()).append(" ")
                .append(usersActiveStats.replaceAll("[(,)]", "")).append(" ")
                .append(UkrCalendar.getFullDate());
        System.out.println(builder);
    }
}
