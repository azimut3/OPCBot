package data.Port;

import data.Subscriprions.Subs;
import data.Subscriprions.UpdateBerths;
import managers.OpcBot;

import java.util.ArrayList;
import java.util.List;

public class PortUpdateThread extends Thread{

    public PortUpdateThread(String name) {
        super(name);
    }

    public void run(){
        while (true) {
            try {
                List<Vessel> tempOldMap = PortContent.portBerths;
                PortContent.portBerths = new ArrayList<>();
                if (!Parser.parsePort()) {
                    Thread.sleep(1000*60*3);
                    PortContent.portBerths = tempOldMap;
                    continue;
                }
                PortContent.oldPortBerths = tempOldMap;
                //System.out.println("Old bertList " + PortContent.oldPortBerths);
                //System.out.println("New bertList " + PortContent.portBerths);
                System.out.println("=== Vessels in port were updated ===");
                if (PortContent.oldPortBerths.size()>0) {
                    UpdateBerths.sendInfoAboutBerths(Subs.users,
                            UpdateBerths.getChangesOnPortUpdate(PortContent.oldPortBerths,
                                    PortContent.portBerths),
                            OpcBot.getOpcBotInstance());
                }
                Thread.sleep(1000*60*10);
            } catch (InterruptedException e) {
                System.out.println("Port thread has been interrupted");
                e.printStackTrace();
            }

        }
    }
}
