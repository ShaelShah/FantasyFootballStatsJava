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

    public double getCatchPer() {
        return catchPer;
    }

    public void setCatchPer(double catchPer) {
        this.catchPer = catchPer;
    }

    public double getYardsPerTar() {
        return yardsPerTar;
    }

    public void setYardsPerTar(double yardsPerTar) {
        this.yardsPerTar = yardsPerTar;
    }

    public double getYardsPerCmp() {
        return yardsPerCat;
    }

    public void setYardsPerCmp(double yardsPerCmp) {
        this.yardsPerCat = yardsPerCmp;
    }

    public int getReceivingTargets() {
        return receivingTargets;
    }

    public void setReceivingTargets(int receivingTargets) {
        this.receivingTargets = receivingTargets;
    }

    public int getReceivingReceptions() {
        return receivingReceptions;
    }

    public void setReceivingReceptions(int receivingReceptions) {
        this.receivingReceptions = receivingReceptions;
    }

    public int getReceivingYards() {
        return receivingYards;
    }

    public void setReceivingYards(int receivingYards) {
        this.receivingYards = receivingYards;
    }

    public int getReceivingYardsAfterCatch() {
        return receivingYardsAfterCatch;
    }

    public void setReceivingYardsAfterCatch(int receivingYardsAfterCatch) {
        this.receivingYardsAfterCatch = receivingYardsAfterCatch;
    }

    public int getReceivingTDs() {
        return receivingTDs;
    }

    public void setReceivingTDs(int receivingTDs) {
        this.receivingTDs = receivingTDs;
    }

    public int getReceivingTwoPts() {
        return receivingTwoPts;
    }

    public void setReceivingTwoPts(int receivingTwoPts) {
        this.receivingTwoPts = receivingTwoPts;
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
