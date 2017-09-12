package stats;

public class MiscStats implements Info {

    private int fumbles;
    private int fumblesLost;

    public MiscStats(int fumbles, int fumblesLost) {
        this.fumbles = fumbles;
        this.fumblesLost = fumblesLost;
    }

    public int getFumbles() {
        return fumbles;
    }

    public int getFumblesLost() {
        return fumblesLost;
    }

    public double getFP() {
        return fumbles * -1 + fumblesLost * -1;
    }

    public String printCSV() {
        return fumbles + "," + fumblesLost + ",";
    }
}
