package data.Subscriprions;

import data.Port.PortContent;
import managers.OpcBot;
import managers.UkrCalendar;


public class BerthSubThread extends Thread {
    int timeMorning = 5;
    int minutesMorning = 0;
    int timeEvening = 17;
    int minutesEvening = 0;

    public BerthSubThread(String name) {
        super(name);
    }

    public void run(){
        boolean firstLaunch = true;
        while (true) {
            try {
                if (firstLaunch){
                    int hours = Integer.parseInt(UkrCalendar.getHours());
                    int minutes = Integer.parseInt(UkrCalendar.getMinutes());
                    System.out.println("time " + hours + ":" + minutes);
                    if (hours < timeMorning){
                        BerthSubThread.sleep(SubsLauncher.getTime(hours, minutes, timeMorning));
                        berthState();
                        System.out.println("=== Berth state was sent(morning) ===");
                        BerthSubThread.sleep(1000*60*60*(timeEvening-timeMorning));
                        berthState();
                        System.out.println("=== Berth state was sent(evening) ===");
                    } else {
                        BerthSubThread.sleep(SubsLauncher.getTime(hours, minutes, timeEvening));
                        berthState();
                        System.out.println("=== Berth state was sent(evening) ===");
                    }
                    firstLaunch = false;
                }
                BerthSubThread.sleep(1000*60*10*((24 - timeEvening) + timeMorning));
                berthState();
                System.out.println("=== Berth state was sent(morning) ===");
                BerthSubThread.sleep(1000*60*60*(timeEvening-timeMorning));
                berthState();
                System.out.println("=== Berth state was sent(evening) ===");

            } catch (InterruptedException e) {
                System.out.println("Berth state thread has been interrupted");
                e.printStackTrace();
            }
        }
    }

    private void berthState() {
        for (String chatId : Subs.users.keySet()) {
            if (Subs.users.get(chatId).get(2).equals("true")) {
                String[] berths = Subs.users.get(chatId).get(1).split(" ");
                StringBuilder builder = new StringBuilder();
                for (String num : berths) {
                    builder.append(PortContent.getBerthByNumber(Integer.valueOf(num)));
                }
                if (builder.length() < 1) builder.append("На выбраных вами причалах судов нет");
                OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(chatId),
                        builder.toString());
            }
        }
    }
}
