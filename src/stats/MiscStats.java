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

    public void setFumbles(int fumbles) {
        this.fumbles = fumbles;
    }

    public int getFumblesLost() {
        return fumblesLost;
    }

    public void setFumblesLost(int fumblesLost) {
        this.fumblesLost = fumblesLost;
    }

    public String printCSV() {
        return fumbles + "," + fumblesLost + ",";
    }
}
