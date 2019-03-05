package data.Port;

import data.Subscriprions.UpdateBerths;
import managers.Parser;

public class PortUpdateThread extends Thread{

    public PortUpdateThread(String name) {
        super(name);
    }

    public void run(){
        while (true) {
            try {
                PortContent.oldPortBerths = PortContent.portBerths;
                PortContent.portBerths.clear();
                if (!Parser.parsePort()) {
                    Thread.sleep(1000*60*3);
                    continue;
                }
                System.out.println("=== Vessels in port were updated ===");
                UpdateBerths.compareResults();
                Thread.sleep(1800000);
            } catch (InterruptedException e) {
                System.out.println("Port thread has been interrupted");
                e.printStackTrace();
            }

        }
    }
}
