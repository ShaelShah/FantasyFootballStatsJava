import stats.*;

public class Week {

    private WeekInfo weekInfo;
    private PassingStats passingStats;
    private ReceivingStats receivingStats;
    private RushingStats rushingStats;
    private MiscStats miscStats;
    private double fantasyPts;

    public Week(WeekInfo weekInfo, PassingStats passingStats, ReceivingStats receivingStats, RushingStats rushingStats, MiscStats miscStats) {
        this.weekInfo = weekInfo;
        this.passingStats = passingStats;
        this.receivingStats = receivingStats;
        this.rushingStats = rushingStats;
        this.miscStats = miscStats;
        this.fantasyPts = passingStats.getFP() + receivingStats.getFP() + rushingStats.getFP() + miscStats.getFP();
    }

    public WeekInfo getWeekInfo() {
        return weekInfo;
    }

    public PassingStats getPassingStats() {
        return passingStats;
    }

    public ReceivingStats getReceivingStats() {
        return receivingStats;
    }

    public RushingStats getRushingStats() {
        return rushingStats;
    }

    public MiscStats getMiscStats() {
        return miscStats;
    }

    public double getFantasyPts() {
        return fantasyPts;
    }

    public String printCSV() {
        return weekInfo.printCSV() + passingStats.printCSV() + receivingStats.printCSV() + rushingStats.printCSV() + miscStats.printCSV() + fantasyPts + ",";
    }
}
