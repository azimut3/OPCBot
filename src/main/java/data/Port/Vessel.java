package data.Port;

public class Vessel {
    private String berth;
    private String vesselName;
    private String date;
    private boolean moored = true;

    public Vessel(){

    }

    public Vessel(String berth, String vesselName, String date){
        this.berth = berth;
        this.vesselName = vesselName;
        this.date = date;
    }

    public String getBerth() {
        return berth;
    }

    public Vessel setBerth(String berth) {

        this.berth = berth;
        return this;
    }

    public String getVesselName() {
        return vesselName;
    }

    public Vessel setVesselName(String vesselName) {

        this.vesselName = vesselName;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Vessel setDate(String date) {

        this.date = date;
        return this;
    }

    public boolean isMoored() {
        return moored;
    }

    public Vessel setMoored(boolean moored) {
        this.moored = moored;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vessel)) return false;
        return getVesselName().equals(((Vessel) obj).getVesselName());
    }
}
