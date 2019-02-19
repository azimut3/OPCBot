package managers;

import data.PortContent;

public class PortUpdate extends Thread{

    public PortUpdate(String name) {
        super(name);
    }

    public void run(){
        while (true) {
            try {
                PortContent.clearTable();
                Parser.parsePort();
                System.out.println("=== Vessels in port were updated ===");
                Thread.sleep(1800000);
            } catch (InterruptedException e) {
                System.out.println("Port thread has been interrupted");
                e.printStackTrace();
            }

        }
    }
}
