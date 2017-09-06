import stats.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Stats {

    public static void main(String args[]) {

        //CSV to Objects
        List<Player> players = csvToObjects();

        //Calculate extra stats
        for (Player p : players) {
            p.calculateTrends();
        }

        //Dump all objects to CSV
        objectsToCSV(players);
    }

    private static List<Player> csvToObjects() {
        List<Player> players = new ArrayList<>();
        try {
            String csvFile = "C:\\Users\\sshah\\Desktop\\FF\\StatsCSV.csv";
            FileReader fileReader = new FileReader(csvFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String parts[] = line.split(",");

                Player player = new Player(parts[0], parts[1]);
                for (int i = 2; i < parts.length; i += 25) {
                    WeekInfo weekInfo = new WeekInfo(parts[i],
                            parts[i + 1],
                            Integer.parseInt(parts[i + 2]),
                            Integer.parseInt(parts[i + 3]),
                            Boolean.parseBoolean(parts[i + 4]));
                    PassingStats passingStats = new PassingStats(
                            Integer.parseInt(parts[i + 5]),
                            Integer.parseInt(parts[i + 6]),
                            Integer.parseInt(parts[i + 7]),
                            Integer.parseInt(parts[i + 8]),
                            Integer.parseInt(parts[i + 9]),
                            Integer.parseInt(parts[i + 10]),
                            Integer.parseInt(parts[i + 11]));
                    ReceivingStats receivingStats = new ReceivingStats(
                            Integer.parseInt(parts[i + 12]),
                            Integer.parseInt(parts[i + 13]),
                            Integer.parseInt(parts[i + 14]),
                            Integer.parseInt(parts[i + 15]),
                            Integer.parseInt(parts[i + 16]),
                            Integer.parseInt(parts[i + 17]));
                    RushingStats rushingStats = new RushingStats(
                            Integer.parseInt(parts[i + 18]),
                            Integer.parseInt(parts[i + 19]),
                            Integer.parseInt(parts[i + 20]),
                            Integer.parseInt(parts[i + 21]),
                            Integer.parseInt(parts[i + 22]));
                    MiscStats miscStats = new MiscStats(
                            Integer.parseInt(parts[i + 23]),
                            Integer.parseInt(parts[i + 24]));

                    Week week = new Week(weekInfo, passingStats,receivingStats, rushingStats, miscStats);
                    player.addWeek(week);
                    players.add(player);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;
    }

    private static void objectsToCSV(List<Player> players) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\sshah\\Desktop\\FF\\AllStatsCSV.csv");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
            Writer writer = new BufferedWriter(outputStreamWriter);

            for (Player p : players) {
                writer.write(p.printCSV() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
