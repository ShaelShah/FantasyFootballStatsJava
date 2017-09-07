package stats;

public class RushingStats implements Info {

    private int rushingAttempts;
    private int rushingYards;
    private int rushingYardsAfterContact;
    private int rushingTDs;
    private int rushingTwoPts;

    private double yardsPerCarry = 0.0;

    public RushingStats(int rushingAttempts, int rushingYards, int rushingYardsAfterContact, int rushingTDs, int rushingTwoPts) {
        this.rushingAttempts = rushingAttempts;
        this.rushingYards = rushingYards;
        this.rushingYardsAfterContact = rushingYardsAfterContact;
        this.rushingTDs = rushingTDs;
        this.rushingTwoPts = rushingTwoPts;

        if (rushingAttempts != 0)
            yardsPerCarry = rushingYards / rushingAttempts;
    }

    public int getRushingAttempts() {
        return rushingAttempts;
    }
    public int getRushingYards() {
        return rushingYards;
    }
    public int getRushingYardsAfterContact() {
        return rushingYardsAfterContact;
    }
    public int getRushingTDs() {
        return rushingTDs;
    }
    public int getRushingTwoPts() {
        return rushingTwoPts;
    }

    public double getFP() {
        return rushingYards * 0.1 + rushingTDs * 6 + rushingTwoPts * 2;
    }

    public String printCSV() {
        return rushingAttempts + "," +
                rushingYards + "," +
                yardsPerCarry + "," +
                rushingYardsAfterContact + "," +
                rushingTDs + "," +
                rushingTwoPts + ",";
    }
}
