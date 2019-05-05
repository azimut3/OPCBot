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
                    int hours = Integer.parseInt(UkrCalendar.getCurrentHours());
                    int minutes = Integer.parseInt(UkrCalendar.getCurrentMinutes());
                    System.out.println("time " + hours + ":" + minutes);
                    if (hours < timeMorning || hours > timeEvening){
                        BerthSubThread.sleep(SubsLauncher.getTime(hours, minutes, timeMorning));
                        berthState();
                        System.out.println("=== Vessel state was sent(morning) ===");
                        BerthSubThread.sleep(1000*60*60*(timeEvening-timeMorning));
                        berthState();
                        System.out.println("=== Vessel state was sent(evening) ===");
                    } else {
                        BerthSubThread.sleep(SubsLauncher.getTime(hours, minutes, timeEvening));
                        berthState();
                        System.out.println("=== Vessel state was sent(evening) ===");
                    }
                    firstLaunch = false;
                }
                BerthSubThread.sleep(1000 * 60 * 60 * ((24 - timeEvening) + timeMorning));
                berthState();
                System.out.println("=== Vessel state was sent(morning) ===");
                BerthSubThread.sleep(1000 * 60 * 60 * (timeEvening - timeMorning));
                berthState();
                System.out.println("=== Vessel state was sent(evening) ===");


            } catch (InterruptedException e) {
                System.out.println("Vessel state thread has been interrupted");
                e.printStackTrace();
            }
        }
    }

    private void berthState() {
        for (String chatId : Subs.users.keySet()) {
            if (Subs.users.get(chatId).getBerthStatusSubscription().equals("true")) {
                String[] berths = Subs.users.get(chatId).getBerthsSelected().split(" ");
                StringBuilder builder = new StringBuilder();
                builder.append("Сводка по порту:").append(System.lineSeparator())
                        .append(System.lineSeparator());
                for (String num : berths) {
                    builder.append(PortContent.getBerthByNumber(num));
                }
                if (builder.length() < 1) builder.append("На выбраных вами причалах судов нет");
                OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(chatId),
                        builder.toString());
            }
        }
    }
}
