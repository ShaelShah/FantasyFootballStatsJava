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

    public double getYardsPerCarry() {
        return yardsPerCarry;
    }

    public void setYardsPerCarry(double yardsPerCarry) {
        this.yardsPerCarry = yardsPerCarry;
    }

    public int getRushingAttempts() {
        return rushingAttempts;
    }

    public void setRushingAttempts(int rushingAttempts) {
        this.rushingAttempts = rushingAttempts;
    }

    public int getRushingYards() {
        return rushingYards;
    }

    public void setRushingYards(int rushingYards) {
        this.rushingYards = rushingYards;
    }

    public int getRushingYardsAfterContact() {
        return rushingYardsAfterContact;
    }

    public void setRushingYardsAfterContact(int rushingYardsAfterContact) {
        this.rushingYardsAfterContact = rushingYardsAfterContact;
    }

    public int getRushingTDs() {
        return rushingTDs;
    }

    public void setRushingTDs(int rushingTDs) {
        this.rushingTDs = rushingTDs;
    }

    public int getRushingTwoPts() {
        return rushingTwoPts;
    }

    public void setRushingTwoPts(int rushingTwoPts) {
        this.rushingTwoPts = rushingTwoPts;
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
