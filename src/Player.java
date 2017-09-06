import java.util.ArrayList;
import java.util.List;

public class Player {

    private String id;
    private String name;
    private List<Week> weeks;

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

    public double getFumblesGm() {
        return fumblesGm;
    }

    public void setFumblesGm(double fumblesGm) {
        this.fumblesGm = fumblesGm;
    }

    public double getFumblesLostGm() {
        return fumblesLostGm;
    }

    public void setFumblesLostGm(double fumblesLostGm) {
        this.fumblesLostGm = fumblesLostGm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }

    public double getPassingAttsGm() {
        return passingAttsGm;
    }

    public void setPassingAttsGm(double passingAttsGm) {
        this.passingAttsGm = passingAttsGm;
    }

    public double getPassingCmpsGm() {
        return passingCmpsGm;
    }

    public void setPassingCmpsGm(double passingCmpsGm) {
        this.passingCmpsGm = passingCmpsGm;
    }

    public double getPassingCmpPerGm() {
        return passingCmpPerGm;
    }

    public void setPassingCmpPerGm(double passingCmpPerGm) {
        this.passingCmpPerGm = passingCmpPerGm;
    }

    public double getPassingYardsGm() {
        return passingYardsGm;
    }

    public void setPassingYardsGm(double passingYardsGm) {
        this.passingYardsGm = passingYardsGm;
    }

    public double getPassingIntsGm() {
        return passingIntsGm;
    }

    public void setPassingIntsGm(double passingIntsGm) {
        this.passingIntsGm = passingIntsGm;
    }

    public double getPassingTDsGm() {
        return passingTDsGm;
    }

    public void setPassingTDsGm(double passingTDsGm) {
        this.passingTDsGm = passingTDsGm;
    }

    public double getPassingTwoPtsGm() {
        return passingTwoPtsGm;
    }

    public void setPassingTwoPtsGm(double passingTwoPtsGm) {
        this.passingTwoPtsGm = passingTwoPtsGm;
    }

    public double getPassingTwoPtAttsGm() {
        return passingTwoPtAttsGm;
    }

    public void setPassingTwoPtAttsGm(double passingTwoPtAttsGm) {
        this.passingTwoPtAttsGm = passingTwoPtAttsGm;
    }

    public double getYardsPerAttGm() {
        return yardsPerAttGm;
    }

    public void setYardsPerAttGm(double yardsPerAttGm) {
        this.yardsPerAttGm = yardsPerAttGm;
    }

    public double getYardsPerCmpGm() {
        return yardsPerCmpGm;
    }

    public void setYardsPerCmpGm(double yardsPerCmpGm) {
        this.yardsPerCmpGm = yardsPerCmpGm;
    }

    public double getRushingAttemptsGm() {
        return rushingAttemptsGm;
    }

    public void setRushingAttemptsGm(double rushingAttemptsGm) {
        this.rushingAttemptsGm = rushingAttemptsGm;
    }

    public double getRushingYardsGm() {
        return rushingYardsGm;
    }

    public void setRushingYardsGm(double rushingYardsGm) {
        this.rushingYardsGm = rushingYardsGm;
    }

    public double getRushingYardsAfterContactGm() {
        return rushingYardsAfterContactGm;
    }

    public void setRushingYardsAfterContactGm(double rushingYardsAfterContactGm) {
        this.rushingYardsAfterContactGm = rushingYardsAfterContactGm;
    }

    public double getRushingTDsGm() {
        return rushingTDsGm;
    }

    public void setRushingTDsGm(double rushingTDsGm) {
        this.rushingTDsGm = rushingTDsGm;
    }

    public double getRushingTwoPtsGm() {
        return rushingTwoPtsGm;
    }

    public void setRushingTwoPtsGm(double rushingTwoPtsGm) {
        this.rushingTwoPtsGm = rushingTwoPtsGm;
    }

    public double getYardsPerCarryGm() {
        return yardsPerCarryGm;
    }

    public void setYardsPerCarryGm(double yardsPerCarryGm) {
        this.yardsPerCarryGm = yardsPerCarryGm;
    }

    public double getReceivingTargetsGm() {
        return receivingTargetsGm;
    }

    public void setReceivingTargetsGm(double receivingTargetsGm) {
        this.receivingTargetsGm = receivingTargetsGm;
    }

    public double getReceivingReceptionsGm() {
        return receivingReceptionsGm;
    }

    public void setReceivingReceptionsGm(double receivingReceptionsGm) {
        this.receivingReceptionsGm = receivingReceptionsGm;
    }

    public double getReceivingYardsGm() {
        return receivingYardsGm;
    }

    public void setReceivingYardsGm(double receivingYardsGm) {
        this.receivingYardsGm = receivingYardsGm;
    }

    public double getReceivingYardsAfterCatchGm() {
        return receivingYardsAfterCatchGm;
    }

    public void setReceivingYardsAfterCatchGm(double receivingYardsAfterCatchGm) {
        this.receivingYardsAfterCatchGm = receivingYardsAfterCatchGm;
    }

    public double getReceivingTDsGm() {
        return receivingTDsGm;
    }

    public void setReceivingTDsGm(double receivingTDsGm) {
        this.receivingTDsGm = receivingTDsGm;
    }

    public double getReceivingTwoPtsGm() {
        return receivingTwoPtsGm;
    }

    public void setReceivingTwoPtsGm(double receivingTwoPtsGm) {
        this.receivingTwoPtsGm = receivingTwoPtsGm;
    }

    public double getCatchPerGm() {
        return catchPerGm;
    }

    public void setCatchPerGm(double catchPerGm) {
        this.catchPerGm = catchPerGm;
    }

    public double getYardsPerTarGm() {
        return yardsPerTarGm;
    }

    public void setYardsPerTarGm(double yardsPerTarGm) {
        this.yardsPerTarGm = yardsPerTarGm;
    }

    public double getYardsPerCatGm() {
        return yardsPerCatGm;
    }

    public void setYardsPerCatGm(double yardsPerCatGm) {
        this.yardsPerCatGm = yardsPerCatGm;
    }

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        weeks = new ArrayList<>();
    }

    public void addWeek(Week week) {
        weeks.add(week);
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

        int totalWeeks = weeks.size();

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

        passingAttsGm = passingAttsTotal / totalWeeks;
        passingCmpsGm = passingCmpsTotal / totalWeeks;
        passingYardsGm = passingYardsTotal / totalWeeks;
        passingIntsGm = passingIntsTotal / totalWeeks;
        passingTDsGm = passingTDsTotal / totalWeeks;
        passingTwoPtsGm = passingTwoPtsTotal / totalWeeks;
        passingTwoPtAttsGm = passingTwoPtAttsTotal / totalWeeks;
        passingCmpPerGm = passingAttsGm / passingCmpsGm;
        yardsPerAttGm = passingYardsGm / passingAttsGm;
        yardsPerCmpGm = passingYardsGm / passingCmpsGm;

        rushingAttemptsGm = rushingAttemptsTotal / totalWeeks;
        rushingYardsGm = rushingYardsTotal / totalWeeks;
        rushingYardsAfterContactGm = rushingYardsAfterContactTotal / totalWeeks;
        rushingTDsGm = rushingTDsTotal / totalWeeks;
        rushingTwoPtsGm = rushingTwoPtsTotal / totalWeeks;
        yardsPerCarryGm = rushingYardsGm / rushingAttemptsGm;

        receivingTargetsGm = receivingTargetsTotal / totalWeeks;
        receivingReceptionsGm = receivingReceptionsTotal / totalWeeks;
        receivingYardsGm = receivingYardsTotal / totalWeeks;
        receivingYardsAfterCatchGm = receivingYardsAfterCatchTotal / totalWeeks;
        receivingTDsGm = receivingTDsTotal / totalWeeks;
        receivingTwoPtsGm = receivingTwoPtsTotal / totalWeeks;
        catchPerGm = receivingReceptionsGm / receivingTargetsGm;
        yardsPerTarGm = receivingYardsGm / receivingTargetsGm;
        yardsPerCatGm = receivingYardsGm / receivingReceptionsGm;

        fumblesGm = fumblesTotal / totalWeeks;
        fumblesLostGm = fumblesLostTotal / totalWeeks;
    }

    public String printCSV() {
        String line = id + "," + name + ",";
        for (Week w : weeks) {
            line += w.printCSV();
        }
        line += passingAttsGm + "," +
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
                fumblesLostGm;

        return line;
    }
}
