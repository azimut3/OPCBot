package managers;

import data.DatabaseConnector.JdbcConnector;
import data.Subscriprions.Subs;
import data.Subscriprions.SubsLauncher;

public class StatsReaper extends Thread{
    public StatsReaper(String name) {
        super(name);
    }

    public void run(){
        boolean firstLaunch = true;
        while (true) {
           if (firstLaunch) {
               int hours = Integer.parseInt(UkrCalendar.getHours());
               int minutes = Integer.parseInt(UkrCalendar.getMinutes());
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

           String usersTotal = String.valueOf(Subs.users.size());
           String usersActiveStats = JdbcConnector.getUsersAndAvgCalls();
           Admin.notifyAdmin("Зарегестрировано пользователей: " + usersTotal);
           Admin.notifyAdmin("Сегодня было активно пользователей (просмотров на пользователя):");
           Admin.notifyAdmin(usersActiveStats);

           Admin.stats.add(Subs.users.size() + " " +
                   usersActiveStats.replaceAll("\\W\\S", "") + " " +
                   UkrCalendar.getFullDate());
           JdbcConnector.resetCalls();
           if (UkrCalendar.getDay().equals(1)) {
               Admin.sendMonthStats();
               Admin.stats.clear();
           }
        }
    }
}
