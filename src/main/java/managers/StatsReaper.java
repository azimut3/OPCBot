package managers;

import data.DatabaseConnector.StatsTable;
import data.DatabaseConnector.UserTableConnector;
import data.Subscriprions.SubsLauncher;

public class StatsReaper extends Thread{
    public StatsReaper(String name) {
        super(name);
    }

    public void run(){
        boolean firstLaunch = true;
        while (true) {
           if (firstLaunch) {
               int hours = Integer.parseInt(UkrCalendar.getCurrentHours());
               int minutes = Integer.parseInt(UkrCalendar.getCurrentMinutes());
               try {
                   StatsReaper.sleep(SubsLauncher.getTime(hours, minutes, 0));
                   firstLaunch = false;
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           } else {
               try {
                   sleep(1000*60*60*24);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }

           Admin.sendStats();
           String usersActiveStats = UserTableConnector.getUsersAndAvgCalls();
           /*StringBuilder builder = new StringBuilder();
            builder.append(Subs.users.size()).append(" ")
                    .append(usersActiveStats.replaceAll("[(,)]", "")).append(" ")
                    .append(UkrCalendar.getCurrentFullDate());
            Admin.stats.add(builder.toString());*/
            StatsTable.recordStats();
           UserTableConnector.resetCalls();
           if (UkrCalendar.getCurrentDay().equals(1)) {
               Admin.sendMonthStats();
               Admin.stats.clear();
           }
        }
    }
}
