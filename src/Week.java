import stats.*;

public class Week {

    private WeekInfo weekInfo;
    private PassingStats passingStats;
    private ReceivingStats receivingStats;
    private RushingStats rushingStats;
    private MiscStats miscStats;

    public Week(WeekInfo weekInfo, PassingStats passingStats, ReceivingStats receivingStats, RushingStats rushingStats, MiscStats miscStats) {
        this.weekInfo = weekInfo;
        this.passingStats = passingStats;
        this.receivingStats = receivingStats;
        this.rushingStats = rushingStats;
        this.miscStats = miscStats;
    }

    public WeekInfo getWeekInfo() {
        return weekInfo;
    }

    public void setWeekInfo(WeekInfo weekInfo) {
        this.weekInfo = weekInfo;
    }

    public PassingStats getPassingStats() {
        return passingStats;
    }

    public void setPassingStats(PassingStats passingStats) {
        this.passingStats = passingStats;
    }

    public ReceivingStats getReceivingStats() {
        return receivingStats;
    }

    public void setReceivingStats(ReceivingStats receivingStats) {
        this.receivingStats = receivingStats;
    }

    public RushingStats getRushingStats() {
        return rushingStats;
    }

    public void setRushingStats(RushingStats rushingStats) {
        this.rushingStats = rushingStats;
    }

    public MiscStats getMiscStats() {
        return miscStats;
    }

    public void setMiscStats(MiscStats miscStats) {
        this.miscStats = miscStats;
    }

    public String printCSV() {
        return weekInfo.printCSV() + passingStats.printCSV() + receivingStats.printCSV() + rushingStats.printCSV() + miscStats.printCSV();
    }
}
