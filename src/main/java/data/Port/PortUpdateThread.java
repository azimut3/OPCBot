package data.Port;

import data.Subscriprions.UpdateBerths;
import managers.Parser;

import java.util.ArrayList;
import java.util.TreeMap;

public class PortUpdateThread extends Thread{

    public PortUpdateThread(String name) {
        super(name);
    }

    public void run(){
        while (true) {
            try {
                TreeMap<Integer, ArrayList<ArrayList<String>>> tempOldMap = PortContent.portBerths;
                PortContent.portBerths = new TreeMap<>();
                if (!Parser.parsePort()) {
                    Thread.sleep(1000*60*3);
                    PortContent.portBerths = tempOldMap;
                    continue;
                }
                PortContent.oldPortBerths = tempOldMap;
                System.out.println("Old bertList " + PortContent.oldPortBerths);
                System.out.println("New bertList " + PortContent.portBerths);
                System.out.println("=== Vessels in port were updated ===");
                UpdateBerths.compareResults();
                Thread.sleep(1000*15);
            } catch (InterruptedException e) {
                System.out.println("Port thread has been interrupted");
                e.printStackTrace();
            }

        }
    }
}
