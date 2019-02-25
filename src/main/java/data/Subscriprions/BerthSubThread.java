package data.Subscriprions;

import data.Port.PortContent;
import managers.OpcBot;
import managers.UkrCalendar;


public class BerthSubThread extends Thread {
    int timeMorning = 5;
    int minutesMorning = 30;
    int timeEvening = 17;
    int minutesEvening = 30;

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
                    System.out.println("Рассылка причалов осуществляется в " + timeMorning + ":" +
                            + minutesMorning + " и " + timeEvening+ ":" + minutesEvening);
                    if (hours < timeMorning) {
                        System.out.println("До утренней рассылки причалов " +
                                (60*(timeMorning-hours) - (60-minutesMorning)));
                        BerthSubThread.sleep(1000*60*60*(timeMorning-hours) -
                                - 1000*60*(60-minutesMorning));
                        berthState();
                        System.out.println("=== Berth state was sent(morning)* ===");
                        BerthSubThread.sleep(1000*60*10*(24 - timeEvening + timeMorning));
                        berthState();
                        System.out.println("=== Berth state was sent(evening)* ===");
                    } else if (hours > timeMorning && hours < timeEvening) {
                        System.out.println("До вечерней рассылки причалов " + (60*(timeEvening-hours) -
                                - (60-minutesEvening)));
                        BerthSubThread.sleep(1000*60*60*(timeEvening-hours) -
                                - 1000*60*(60-minutesEvening));
                        berthState();
                        System.out.println("=== Berth state was sent(evening)* ===");
                    } else {
                        //if (minutes > 0) hours++;
                        System.out.println("До утренней рассылки причалов "
                                + (60*(24 - hours + timeMorning) - (60-minutesMorning)));
                        BerthSubThread.sleep(1000*60*60*(24 - hours + timeMorning) -
                                - 1000*60*(60-minutesMorning));
                        berthState();
                        System.out.println("=== Berth state was sent(morning)* ===");
                        BerthSubThread.sleep(1000*60*10*(24 - timeEvening + timeMorning));
                        berthState();
                        System.out.println("=== Berth state was sent(evening)* ===");
                    }
                    firstLaunch = false;
                }
                BerthSubThread.sleep(1000*60*60*(timeEvening-timeMorning));
                berthState();
                System.out.println("=== Berth state was sent(morning) ===");
                BerthSubThread.sleep(1000*60*10*(24 - timeEvening + timeMorning));
                berthState();
                System.out.println("=== Berth state was sent(evening) ===");

            } catch (InterruptedException e) {
                System.out.println("Berth state thread has been interrupted");
                e.printStackTrace();
            }

        }
    }

    private void berthState() {
        for (String chatId : Subs.berthSubs.keySet()) {
            String[] berths = Subs.berthSubs.get(chatId).split(" ");
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
