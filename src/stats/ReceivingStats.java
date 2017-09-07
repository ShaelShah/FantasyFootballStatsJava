package stats;

public class ReceivingStats implements Info {

    private int receivingTargets;
    private int receivingReceptions;
    private int receivingYards;
    private int receivingYardsAfterCatch;
    private int receivingTDs;
    private int receivingTwoPts;

    private double catchPer = 0.0;
    private double yardsPerTar = 0.0;
    private double yardsPerCat = 0.0;

    public ReceivingStats(int receivingTargets, int receivingReceptions, int receivingYards, int receivingYardsAfterCatch, int receivingTDs, int receivingTwoPts) {
        this.receivingTargets = receivingTargets;
        this.receivingReceptions = receivingReceptions;
        this.receivingYards = receivingYards;
        this.receivingYardsAfterCatch = receivingYardsAfterCatch;
        this.receivingTDs = receivingTDs;
        this.receivingTwoPts = receivingTwoPts;

        if (receivingTargets != 0) {
            catchPer = receivingReceptions / receivingTargets;
            yardsPerTar = receivingYards / receivingTargets;
        }

        if (yardsPerCat != 0)
            yardsPerCat = receivingYards / receivingReceptions;
    }

    public int getReceivingTargets() {
        return receivingTargets;
    }
    public int getReceivingReceptions() {
        return receivingReceptions;
    }
    public int getReceivingYards() {
        return receivingYards;
    }
    public int getReceivingYardsAfterCatch() {
        return receivingYardsAfterCatch;
    }
    public int getReceivingTDs() {
        return receivingTDs;
    }
    public int getReceivingTwoPts() {
        return receivingTwoPts;
    }

    public double getFP() {
        return receivingYards * 0.1 + receivingTDs * 6 + receivingTwoPts * 2;
    }

    public String printCSV() {
        return receivingTargets + "," +
                receivingReceptions + "," +
                catchPer + "," +
                receivingYards + "," +
                yardsPerTar + "," +
                yardsPerCat + "," +
                receivingYardsAfterCatch + "," +
                receivingTDs + "," +
                receivingTwoPts + ",";
    }
}
