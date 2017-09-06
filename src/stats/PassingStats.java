package stats;

public class PassingStats implements Info {

    private int passingAttempts;
    private int passingCompletions;
    private int passingYards;
    private int passingInts;
    private int passingTDs;
    private int passingTwoPts;
    private int passingTwoPtAtts;

    private double passingCmpPer = 0.0;
    private double yardsPerAtt = 0.0;
    private double yardsPerCmp = 0.0;

    public PassingStats(int passingAttempts, int passingCompletions, int passingYards, int passingInts, int passingTDs, int passingTwoPts, int passingTwoPtAtts) {
        this.passingAttempts = passingAttempts;
        this.passingCompletions = passingCompletions;
        this.passingYards = passingYards;
        this.passingInts = passingInts;
        this.passingTDs = passingTDs;
        this.passingTwoPts = passingTwoPts;
        this.passingTwoPtAtts = passingTwoPtAtts;

        if (passingAttempts != 0) {
            passingCmpPer = passingCompletions / passingAttempts;
            yardsPerAtt = passingYards / passingAttempts;
        }

        if (passingCompletions != 0)
            yardsPerCmp = passingYards / passingCompletions;
    }

    public int getPassingCompletions() {
        return passingCompletions;
    }

    public int getPassingYards() {
        return passingYards;
    }

    public int getPassingInts() {
        return passingInts;
    }

    public int getPassingTDs() {
        return passingTDs;
    }

    public int getPassingTwoPts() {
        return passingTwoPts;
    }

    public int getPassingTwoPtAtts() {
        return passingTwoPtAtts;
    }

    public void setPassingCompletions(int passingCompletions) {
        this.passingCompletions = passingCompletions;
    }

    public void setPassingYards(int passingYards) {
        this.passingYards = passingYards;
    }

    public void setPassingInts(int passingInts) {
        this.passingInts = passingInts;
    }

    public void setPassingTDs(int passingTDs) {
        this.passingTDs = passingTDs;
    }

    public void setPassingTwoPts(int passingTwoPts) {
        this.passingTwoPts = passingTwoPts;
    }

    public void setPassingTwoPtAtts(int passingTwoPtAtts) {
        this.passingTwoPtAtts = passingTwoPtAtts;
    }

    public int getPassingAttempts() {
        return passingAttempts;
    }

    public void setPassingAttempts(int passingAttempts) {
        this.passingAttempts = passingAttempts;
    }

    public double getPassingCmpPer() {
        return passingCmpPer;
    }

    public void setPassingCmpPer(double passingCmpPer) {
        this.passingCmpPer = passingCmpPer;
    }

    public double getYardsPerAtt() {
        return yardsPerAtt;
    }

    public void setYardsPerAtt(double yardsPerAtt) {
        this.yardsPerAtt = yardsPerAtt;
    }

    public double getYardsPerCmp() {
        return yardsPerCmp;
    }

    public void setYardsPerCmp(double yardsPerCmp) {
        this.yardsPerCmp = yardsPerCmp;
    }

    public String printCSV() {
        return passingAttempts + "," +
                passingCompletions + "," +
                passingCmpPer + "," +
                passingYards + "," +
                yardsPerAtt + "," +
                yardsPerCmp + "," +
                passingInts + "," +
                passingTDs + "," +
                passingTwoPts + "," +
                passingTwoPtAtts + ",";
    }
}
