import stats.*;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String id;
    private String name;
    private List<Week> weeks;

    // Games Played
    private double gamesPlayed;

    // Trend stats - Season totals and per game
    // Passing
    private double attemptsGm = 0.0;
    private double completionsGm = 0.0;
    private double cmpPerGm = 0.0;
    private double pYardsGm = 0.0;
    private double interceptionsGm = 0.0;
    private double pTdsGm = 0.0;
    private double pTwoPtsGm = 0.0;
    private double pTwoPtAttsGm = 0.0;
    private double yardsPerAttGm = 0.0;
    private double yardsPerCmpGm = 0.0;

    private double attemptsTot = 0.0;
    private double completionsTot = 0.0;
    private double pYardsTot = 0.0;
    private double interceptionsTot = 0.0;
    private double pTdsTot = 0.0;
    private double pTwoPtsTot = 0.0;
    private double pTwoPtAttsTot = 0.0;

    //Receiving
    private double targetsGm = 0.0;
    private double receptionsGm = 0.0;
    private double rYardsGm = 0.0;
    private double yardsAfterCatchGm = 0.0;
    private double rTdsGm = 0.0;
    private double rTwoPtsGm = 0.0;
    private double recPerGm = 0.0;
    private double yardsPerTarGm = 0.0;
    private double yardsPerRecGm = 0.0;

    private double targetsTot = 0.0;
    private double receptionsTot = 0.0;
    private double rYardsTot = 0.0;
    private double yardsAfterCatchTot = 0.0;
    private double rTdsTot = 0.0;
    private double rTwoPtsTot = 0.0;

    //Rushing
    private double carriesGm = 0.0;
    private double ruYardsGm = 0.0;
    private double yardsAfterContactGm = 0.0;
    private double ruTdsGm = 0.0;
    private double ruTwoPtsGm = 0.0;
    private double yardsPerCarryGm = 0.0;

    private double carriesTot = 0.0;
    private double ruYardsTot = 0.0;
    private double yardsAfterContactTot = 0.0;
    private double ruTdsTot = 0.0;
    private double ruTwoPtsTot = 0.0;

    //Misc
    private double fumblesGm = 0.0;
    private double fumblesLostGm = 0.0;

    private double fumblesTot = 0.0;
    private double fumblesLostTot = 0.0;

    //Fantasy Points
    private double fantasyPtsTotal = 0.0;
    private double fantasyPtsGm = 0.0;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        weeks = new ArrayList<>();
    }

    public void addWeek(Week week) {
        weeks.add(week);
    }

    public void calculateGamesPlayed() {
        gamesPlayed = weeks.size();
    }

    public void addEmptyWeeks(int finalWeek) {
        WeekInfo lastWeek = weeks.get(weeks.size() - 1).getWeekInfo();
        WeekInfo week17 = new WeekInfo(lastWeek.getTeam(), lastWeek.getPosition(), lastWeek.getYear(), finalWeek, lastWeek.isHome());
        PassingStats passingStats = new PassingStats(0, 0, 0, 0, 0, 0, 0);
        ReceivingStats receivingStats = new ReceivingStats(0, 0, 0, 0, 0, 0);
        RushingStats rushingStats = new RushingStats(0, 0, 0, 0, 0);
        MiscStats miscStats = new MiscStats(0, 0);

        if (lastWeek.getWeek() != finalWeek) {
            weeks.add(new Week(week17, passingStats, receivingStats, rushingStats, miscStats));
        }

        if (weeks.size() < finalWeek) {
            int startWeek = 1;
            for (int i = 0; i < weeks.size(); ) {
                WeekInfo currentWeekInfo = weeks.get(i).getWeekInfo();
                int currentWeek = currentWeekInfo.getWeek();

                for (int j = startWeek; j < currentWeek; j++) {
                    WeekInfo weekInfo = new WeekInfo(currentWeekInfo.getTeam(), currentWeekInfo.getPosition(), currentWeekInfo.getYear(), j, currentWeekInfo.isHome());
                    weeks.add(j - 1, new Week(weekInfo, passingStats, receivingStats, rushingStats, miscStats));
                    i++;
                }

                startWeek = currentWeek + 1;
                i++;
            }
        }
    }

    public void calculateTrends() {
        for (Week w : weeks) {
            attemptsTot += w.getPassingStats().getAttempts();
            completionsTot += w.getPassingStats().getCompletions();
            pYardsTot += w.getPassingStats().getYards();
            interceptionsTot += w.getPassingStats().getInterceptions();
            pTdsTot += w.getPassingStats().getTds();
            pTwoPtsTot += w.getPassingStats().getTwoPts();
            pTwoPtAttsTot += w.getPassingStats().getTwoPtAtts();

            carriesTot += w.getRushingStats().getCarries();
            ruYardsTot += w.getRushingStats().getYards();
            yardsAfterContactTot += w.getRushingStats().getYardsAfterContact();
            ruTdsTot += w.getRushingStats().getTds();
            ruTwoPtsTot += w.getRushingStats().getTwoPts();

            targetsTot += w.getReceivingStats().getTargets();
            receptionsTot += w.getReceivingStats().getReceptions();
            rYardsTot += w.getReceivingStats().getYards();
            yardsAfterCatchTot += w.getReceivingStats().getYardsAfterCatch();
            rTdsTot += w.getReceivingStats().getTds();
            rTwoPtsTot += w.getReceivingStats().getTwoPts();

            fumblesTot += w.getMiscStats().getFumbles();
            fumblesLostTot += w.getMiscStats().getFumblesLost();
        }

        attemptsGm = attemptsTot / gamesPlayed;
        completionsGm = completionsTot / gamesPlayed;
        pYardsGm = pYardsTot / gamesPlayed;
        interceptionsGm = interceptionsTot / gamesPlayed;
        pTdsGm = pTdsTot / gamesPlayed;
        pTwoPtsGm = pTwoPtsTot / gamesPlayed;
        pTwoPtAttsGm = pTwoPtAttsTot / gamesPlayed;
        cmpPerGm = completionsGm / attemptsGm;
        yardsPerAttGm = pYardsGm / attemptsGm;
        yardsPerCmpGm = pYardsGm / completionsGm;

        carriesGm = carriesTot / gamesPlayed;
        ruYardsGm = ruYardsTot / gamesPlayed;
        yardsAfterContactGm = yardsAfterContactTot / gamesPlayed;
        ruTdsGm = rTdsTot / gamesPlayed;
        ruTwoPtsGm = ruTwoPtsTot / gamesPlayed;
        yardsPerCarryGm = ruYardsGm / attemptsGm;

        targetsGm = targetsTot / gamesPlayed;
        receptionsGm = receptionsTot / gamesPlayed;
        rYardsGm = rYardsTot / gamesPlayed;
        yardsAfterCatchGm = yardsAfterCatchTot / gamesPlayed;
        rTdsGm = rTdsTot / gamesPlayed;
        rTwoPtsGm = rTwoPtsTot / gamesPlayed;
        recPerGm = receptionsGm / targetsGm;
        yardsPerTarGm = rYardsGm / targetsGm;
        yardsPerRecGm = rYardsGm / receptionsGm;

        fumblesGm = fumblesTot / gamesPlayed;
        fumblesLostGm = fumblesLostTot / gamesPlayed;

        fantasyPtsTotal = pYardsTot * 0.04 + pTdsTot * 4 + interceptionsTot * -2 + pTwoPtsTot * 2 +
                          rYardsTot * 0.1 + rTdsTot * 6 + rTwoPtsTot * 2 +
                          ruYardsTot * 0.1 + ruTdsTot * 6 + ruTwoPtsTot * 2 +
                          fumblesTot * -1 + fumblesLostTot * -1;
        fantasyPtsGm = fantasyPtsTotal / gamesPlayed;
    }

    public String printCSV() {
        StringBuilder stringBuilder = new StringBuilder(id + "," + name + ",");
        for (Week w : weeks) {
            stringBuilder.append(w.printCSV());
        }
        stringBuilder.append(attemptsGm + "," + completionsGm + "," + cmpPerGm + "," + pYardsGm + "," + interceptionsGm + "," + pTdsGm + "," + pTwoPtsGm + "," + pTwoPtAttsGm + "," + yardsPerAttGm + "," + yardsPerCmpGm + "," + targetsGm + "," + receptionsGm + "," + rYardsGm + "," + yardsAfterCatchGm + "," + rTdsGm + "," + rTwoPtsGm + "," + recPerGm + "," + yardsPerTarGm + "," + yardsPerRecGm + "," + carriesGm + "," + ruYardsGm + "," + yardsAfterContactGm + "," + ruTdsGm + "," + ruTwoPtsGm + "," + yardsPerCarryGm + "," + fumblesGm + "," + fumblesLostGm + "," + fantasyPtsGm);
        stringBuilder.append(attemptsTot + "," + completionsTot + "," + pYardsTot + "," + interceptionsTot + "," + pTdsTot + "," + pTwoPtsTot + "," + pTwoPtAttsTot + "," + targetsTot + "," + receptionsTot + "," + rYardsTot + "," + yardsAfterCatchTot + "," + rTdsTot + "," + rTwoPtsTot + "," + carriesTot + "," + ruYardsTot + "," + yardsAfterContactTot + "," + ruTdsTot + "," + ruTwoPtsTot + "," + fumblesTot + "," + fumblesLostTot + "," + fantasyPtsTotal);

        return stringBuilder.toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Week> getWeeks() {
        return weeks;
    }

    public double getGamesPlayed() {
        return gamesPlayed;
    }

    public double getAttemptsGm() {
        return attemptsGm;
    }

    public double getCompletionsGm() {
        return completionsGm;
    }

    public double getCmpPerGm() {
        return cmpPerGm;
    }

    public double getpYardsGm() {
        return pYardsGm;
    }

    public double getInterceptionsGm() {
        return interceptionsGm;
    }

    public double getpTdsGm() {
        return pTdsGm;
    }

    public double getpTwoPtsGm() {
        return pTwoPtsGm;
    }

    public double getpTwoPtAttsGm() {
        return pTwoPtAttsGm;
    }

    public double getYardsPerAttGm() {
        return yardsPerAttGm;
    }

    public double getYardsPerCmpGm() {
        return yardsPerCmpGm;
    }

    public double getAttemptsTot() {
        return attemptsTot;
    }

    public double getCompletionsTot() {
        return completionsTot;
    }

    public double getpYardsTot() {
        return pYardsTot;
    }

    public double getInterceptionsTot() {
        return interceptionsTot;
    }

    public double getpTdsTot() {
        return pTdsTot;
    }

    public double getpTwoPtsTot() {
        return pTwoPtsTot;
    }

    public double getpTwoPtAttsTot() {
        return pTwoPtAttsTot;
    }

    public double getTargetsGm() {
        return targetsGm;
    }

    public double getReceptionsGm() {
        return receptionsGm;
    }

    public double getrYardsGm() {
        return rYardsGm;
    }

    public double getYardsAfterCatchGm() {
        return yardsAfterCatchGm;
    }

    public double getrTdsGm() {
        return rTdsGm;
    }

    public double getrTwoPtsGm() {
        return rTwoPtsGm;
    }

    public double getRecPerGm() {
        return recPerGm;
    }

    public double getYardsPerTarGm() {
        return yardsPerTarGm;
    }

    public double getYardsPerRecGm() {
        return yardsPerRecGm;
    }

    public double getTargetsTot() {
        return targetsTot;
    }

    public double getReceptionsTot() {
        return receptionsTot;
    }

    public double getrYardsTot() {
        return rYardsTot;
    }

    public double getYardsAfterCatchTot() {
        return yardsAfterCatchTot;
    }

    public double getrTdsTot() {
        return rTdsTot;
    }

    public double getrTwoPtsTot() {
        return rTwoPtsTot;
    }

    public double getCarriesGm() {
        return carriesGm;
    }

    public double getRuYardsGm() {
        return ruYardsGm;
    }

    public double getYardsAfterContactGm() {
        return yardsAfterContactGm;
    }

    public double getRuTdsGm() {
        return ruTdsGm;
    }

    public double getRuTwoPtsGm() {
        return ruTwoPtsGm;
    }

    public double getYardsPerCarryGm() {
        return yardsPerCarryGm;
    }

    public double getCarriesTot() {
        return carriesTot;
    }

    public double getRuYardsTot() {
        return ruYardsTot;
    }

    public double getYardsAfterContactTot() {
        return yardsAfterContactTot;
    }

    public double getRuTdsTot() {
        return ruTdsTot;
    }

    public double getRuTwoPtsTot() {
        return ruTwoPtsTot;
    }

    public double getFumblesGm() {
        return fumblesGm;
    }

    public double getFumblesLostGm() {
        return fumblesLostGm;
    }

    public double getFumblesTot() {
        return fumblesTot;
    }

    public double getFumblesLostTot() {
        return fumblesLostTot;
    }

    public double getFantasyPtsTotal() {
        return fantasyPtsTotal;
    }

    public double getFantasyPtsGm() {
        return fantasyPtsGm;
    }
}
