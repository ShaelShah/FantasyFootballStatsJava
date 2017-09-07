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

    public String getPosition() {
        return position;
    }

    public int getYear() {
        return year;
    }

    public boolean isHome() {
        return home;
    }

    public int getWeek() {
        return week;
    }

    public String printCSV() {
        return team + "," + position + "," + year + "," + week + "," + home + ",";
    }
}
