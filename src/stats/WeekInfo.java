package stats;

public class WeekInfo implements Info {

    private String team;
    private String position;
    private int year;
    private int week;
    private boolean home;

    public WeekInfo(String team, String position, int year, int week, boolean home) {
        this.team = team;
        this.position = position;
        this.year = year;
        this.week = week;
        this.home = home;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public boolean isHome() {
        return home;
    }

    public void setHome(boolean home) {
        this.home = home;
    }

    public String printCSV() {
        return team + "," + position + "," + year + "," + week + "," + home + ",";
    }
}
