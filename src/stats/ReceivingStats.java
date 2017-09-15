package stats;

public class ReceivingStats implements Info {

    private int targets;
    private int receptions;
    private int yards;
    private int yardsAfterCatch;
    private int tds;
    private int twoPts;

    private double recPer = 0.0;
    private double yardsPerTar = 0.0;
    private double yardsPerRec = 0.0;

    public ReceivingStats(int targets, int receptions, int yards, int yardsAfterCatch, int tds, int twoPts) {
        this.targets = targets;
        this.receptions = receptions;
        this.yards = yards;
        this.yardsAfterCatch = yardsAfterCatch;
        this.tds = tds;
        this.twoPts = twoPts;

        if (targets != 0) {
            recPer = (double) receptions / targets;
            yardsPerTar = (double) yards / targets;
        }

        if (yardsPerRec != 0)
            yardsPerRec = (double) yards / receptions;
    }

    public int getTargets() {
        return targets;
    }

    public int getReceptions() {
        return receptions;
    }

    public int getYards() {
        return yards;
    }

    public int getYardsAfterCatch() {
        return yardsAfterCatch;
    }

    public int getTds() {
        return tds;
    }

    public int getTwoPts() {
        return twoPts;
    }

    public double getRecPer() {
        return recPer;
    }

    public double getYardsPerTar() {
        return yardsPerTar;
    }

    public double getYardsPerRec() {
        return yardsPerRec;
    }

    public double getFP() {
        return (yards * 0.1) + (tds * 6) + (twoPts * 2);
    }

    public String printCSV() {
        return targets + "," +
                receptions + "," +
                recPer + "," +
                yards + "," +
                yardsPerTar + "," +
                yardsPerRec + "," +
                yardsAfterCatch + "," +
                tds + "," +
                twoPts + ",";
    }
}
