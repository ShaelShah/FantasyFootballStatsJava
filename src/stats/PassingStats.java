package stats;

public class PassingStats implements Info {

    private int attempts;
    private int completions;
    private int yards;
    private int interceptions;
    private int tds;
    private int twoPts;
    private int twoPtAtts;

    private double cmpPer = 0.0;
    private double yardsPerAtt = 0.0;
    private double yardsPerCmp = 0.0;

    public PassingStats(int attempts, int completions, int yards, int interceptions, int tds, int twoPts, int twoPtAtts) {
        this.attempts = attempts;
        this.completions = completions;
        this.yards = yards;
        this.interceptions = interceptions;
        this.tds = tds;
        this.twoPts = twoPts;
        this.twoPtAtts = twoPtAtts;

        if (attempts != 0) {
            cmpPer = completions / attempts;
            yardsPerAtt = yards / attempts;
        }

        if (completions != 0)
            yardsPerCmp = yards / completions;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getCompletions() {
        return completions;
    }

    public int getYards() {
        return yards;
    }

    public int getInterceptions() {
        return interceptions;
    }

    public int getTds() {
        return tds;
    }

    public int getTwoPts() {
        return twoPts;
    }

    public int getTwoPtAtts() {
        return twoPtAtts;
    }

    public double getCmpPer() {
        return cmpPer;
    }

    public double getYardsPerAtt() {
        return yardsPerAtt;
    }

    public double getYardsPerCmp() {
        return yardsPerCmp;
    }

    public double getFP() {
        return yards * 0.04 + tds * 4 + interceptions * -2 + twoPts * 2;
    }

    public String printCSV() {
        return attempts + "," +
                completions + "," +
                cmpPer + "," +
                yards + "," +
                yardsPerAtt + "," +
                yardsPerCmp + "," +
                interceptions + "," +
                tds + "," +
                twoPts + "," +
                twoPtAtts + ",";
    }
}
