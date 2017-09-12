package stats;

public class RushingStats implements Info {

    private int carries;
    private int yards;
    private int yardsAfterContact;
    private int tds;
    private int twoPts;

    private double yardsPerCarry = 0.0;

    public RushingStats(int carries, int yards, int yardsAfterContact, int tds, int twoPts) {
        this.carries = carries;
        this.yards = yards;
        this.yardsAfterContact = yardsAfterContact;
        this.tds = tds;
        this.twoPts = twoPts;

        if (carries != 0)
            yardsPerCarry = yards / carries;
    }

    public int getCarries() {
        return carries;
    }

    public int getYards() {
        return yards;
    }

    public int getYardsAfterContact() {
        return yardsAfterContact;
    }

    public int getTds() {
        return tds;
    }

    public int getTwoPts() {
        return twoPts;
    }

    public double getYardsPerCarry() {
        return yardsPerCarry;
    }

    public double getFP() {
        return yards * 0.1 + tds * 6 + twoPts * 2;
    }

    public String printCSV() {
        return carries + "," +
                yards + "," +
                yardsPerCarry + "," +
                yardsAfterContact + "," +
                tds + "," +
                twoPts + ",";
    }
}
