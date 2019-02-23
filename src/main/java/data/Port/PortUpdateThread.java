package data.Port;

import data.Subscriprions.Subs;
import data.Subscriprions.UpdateBerths;
import managers.Parser;

import java.util.ArrayList;
import java.util.Set;

public class PortUpdateThread extends Thread{

    public PortUpdateThread(String name) {
        super(name);
    }

    public void run(){
        while (true) {
            try {
                PortContent.oldPortBerths = PortContent.portBerths;
                PortContent.clearTable();
                Parser.parsePort();
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
