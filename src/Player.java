import stats.*;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String id;
    private String name;
    private List<Week> weeks;

    // Games Played
    private double gamesPlayed;

    // Trend stats
    private double passingAttsGm;
    private double passingCmpsGm;
    private double passingCmpPerGm;
    private double passingYardsGm;
    private double passingIntsGm;
    private double passingTDsGm;
    private double passingTwoPtsGm;
    private double passingTwoPtAttsGm;
    private double yardsPerAttGm;
    private double yardsPerCmpGm;

    private double receivingTargetsGm;
    private double receivingReceptionsGm;
    private double receivingYardsGm;
    private double receivingYardsAfterCatchGm;
    private double receivingTDsGm;
    private double receivingTwoPtsGm;
    private double catchPerGm;
    private double yardsPerTarGm;
    private double yardsPerCatGm;

    private double rushingAttemptsGm;
    private double rushingYardsGm;
    private double rushingYardsAfterContactGm;
    private double rushingTDsGm;
    private double rushingTwoPtsGm;
    private double yardsPerCarryGm;

    private double fumblesGm;
    private double fumblesLostGm;

    private double fantasyPtsTotal;
    private double fantasyPtsGm;

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

    public void addEmptyWeeks() {

        WeekInfo lastWeek = weeks.get(weeks.size() - 1).getWeekInfo();
        WeekInfo week17 = new WeekInfo(lastWeek.getTeam(), lastWeek.getPosition(), lastWeek.getYear(), 17, lastWeek.isHome());
        PassingStats passingStats = new PassingStats(0, 0, 0, 0, 0, 0, 0);
        ReceivingStats receivingStats = new ReceivingStats(0, 0, 0, 0, 0, 0);
        RushingStats rushingStats = new RushingStats(0, 0, 0, 0, 0);
        MiscStats miscStats = new MiscStats(0, 0);

        if (lastWeek.getWeek() != 17) {
            weeks.add(new Week(week17, passingStats, receivingStats, rushingStats, miscStats));
        }

        if (weeks.size() < 17) {
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
        double passingAttsTotal = 0;
        double passingCmpsTotal = 0;
        double passingYardsTotal = 0;
        double passingIntsTotal = 0;
        double passingTDsTotal = 0;
        double passingTwoPtsTotal = 0;
        double passingTwoPtAttsTotal = 0;

        double rushingAttemptsTotal = 0;
        double rushingYardsTotal = 0;
        double rushingYardsAfterContactTotal = 0;
        double rushingTDsTotal = 0;
        double rushingTwoPtsTotal = 0;

        double receivingTargetsTotal = 0;
        double receivingReceptionsTotal = 0;
        double receivingYardsTotal = 0;
        double receivingYardsAfterCatchTotal = 0;
        double receivingTDsTotal = 0;
        double receivingTwoPtsTotal = 0;

        double fumblesTotal = 0;
        double fumblesLostTotal = 0;

        for (Week w : weeks) {
            passingAttsTotal += w.getPassingStats().getPassingAttempts();
            passingCmpsTotal += w.getPassingStats().getPassingCompletions();
            passingYardsTotal += w.getPassingStats().getPassingYards();
            passingIntsTotal += w.getPassingStats().getPassingInts();
            passingTDsTotal += w.getPassingStats().getPassingTDs();
            passingTwoPtsTotal += w.getPassingStats().getPassingTwoPts();
            passingTwoPtAttsTotal += w.getPassingStats().getPassingTwoPtAtts();

            rushingAttemptsTotal += w.getRushingStats().getRushingAttempts();
            rushingYardsTotal += w.getRushingStats().getRushingYards();
            rushingYardsAfterContactTotal += w.getRushingStats().getRushingYardsAfterContact();
            rushingTDsTotal += w.getRushingStats().getRushingTDs();
            rushingTwoPtsTotal += w.getRushingStats().getRushingTwoPts();

            receivingTargetsTotal += w.getReceivingStats().getReceivingTargets();
            receivingReceptionsTotal += w.getReceivingStats().getReceivingReceptions();
            receivingYardsTotal += w.getReceivingStats().getReceivingYards();
            receivingYardsAfterCatchTotal += w.getReceivingStats().getReceivingYardsAfterCatch();
            receivingTDsTotal += w.getReceivingStats().getReceivingTDs();
            receivingTwoPtsTotal += w.getReceivingStats().getReceivingTwoPts();

            fumblesTotal += w.getMiscStats().getFumbles();
            fumblesLostTotal += w.getMiscStats().getFumblesLost();
        }

        passingAttsGm = passingAttsTotal / gamesPlayed;
        passingCmpsGm = passingCmpsTotal / gamesPlayed;
        passingYardsGm = passingYardsTotal / gamesPlayed;
        passingIntsGm = passingIntsTotal / gamesPlayed;
        passingTDsGm = passingTDsTotal / gamesPlayed;
        passingTwoPtsGm = passingTwoPtsTotal / gamesPlayed;
        passingTwoPtAttsGm = passingTwoPtAttsTotal / gamesPlayed;
        passingCmpPerGm = passingAttsGm / passingCmpsGm;
        yardsPerAttGm = passingYardsGm / passingAttsGm;
        yardsPerCmpGm = passingYardsGm / passingCmpsGm;

        rushingAttemptsGm = rushingAttemptsTotal / gamesPlayed;
        rushingYardsGm = rushingYardsTotal / gamesPlayed;
        rushingYardsAfterContactGm = rushingYardsAfterContactTotal / gamesPlayed;
        rushingTDsGm = rushingTDsTotal / gamesPlayed;
        rushingTwoPtsGm = rushingTwoPtsTotal / gamesPlayed;
        yardsPerCarryGm = rushingYardsGm / rushingAttemptsGm;

        receivingTargetsGm = receivingTargetsTotal / gamesPlayed;
        receivingReceptionsGm = receivingReceptionsTotal / gamesPlayed;
        receivingYardsGm = receivingYardsTotal / gamesPlayed;
        receivingYardsAfterCatchGm = receivingYardsAfterCatchTotal / gamesPlayed;
        receivingTDsGm = receivingTDsTotal / gamesPlayed;
        receivingTwoPtsGm = receivingTwoPtsTotal / gamesPlayed;
        catchPerGm = receivingReceptionsGm / receivingTargetsGm;
        yardsPerTarGm = receivingYardsGm / receivingTargetsGm;
        yardsPerCatGm = receivingYardsGm / receivingReceptionsGm;

        fumblesGm = fumblesTotal / gamesPlayed;
        fumblesLostGm = fumblesLostTotal / gamesPlayed;

        fantasyPtsTotal = passingYardsTotal * 0.04 + passingTDsTotal * 4 + passingIntsTotal * -2 + passingTwoPtsTotal * 2 +
                          receivingYardsTotal * 0.1 + receivingTDsTotal * 6 + receivingTwoPtsTotal * 2 +
                          rushingYardsTotal * 0.1 + rushingTDsTotal * 6 + rushingTwoPtsTotal * 2 +
                          fumblesTotal * -1 + fumblesLostTotal * -1;
        fantasyPtsGm = fantasyPtsTotal / gamesPlayed;
    }

    public String printCSV() {
        StringBuilder stringBuilder = new StringBuilder(id + "," + name + ",");
        for (Week w : weeks) {
            stringBuilder.append(w.printCSV());
        }
        stringBuilder.append(passingAttsGm + "," +
                passingCmpsGm + "," +
                passingCmpPerGm + "," +
                passingYardsGm + "," +
                passingIntsGm + "," +
                passingTDsGm + "," +
                passingTwoPtsGm + "," +
                passingTwoPtAttsGm + "," +
                yardsPerAttGm + "," +
                yardsPerCmpGm + "," +
                receivingTargetsGm + "," +
                receivingReceptionsGm + "," +
                receivingYardsGm + "," +
                receivingYardsAfterCatchGm + "," +
                receivingTDsGm + "," +
                receivingTwoPtsGm + "," +
                catchPerGm + "," +
                yardsPerTarGm + "," +
                yardsPerCatGm + "," +
                rushingAttemptsGm + "," +
                rushingYardsGm + "," +
                rushingYardsAfterContactGm + "," +
                rushingTDsGm + "," +
                rushingTwoPtsGm + "," +
                yardsPerCarryGm + "," +
                fumblesGm + "," +
                fumblesLostGm + "," +
                fantasyPtsTotal + "," +
                fantasyPtsGm);

        return stringBuilder.toString();
    }
}
