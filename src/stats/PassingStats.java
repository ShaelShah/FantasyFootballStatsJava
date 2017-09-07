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
    public int getPassingAttempts() {
        return passingAttempts;
    }

    public double getFP() {
        return passingYards * 0.04 + passingTDs * 4 + passingInts * -2 + passingTwoPts * 2;
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
