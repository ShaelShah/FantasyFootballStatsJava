import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import stats.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Stats {

    private static final int finalWeek = 17;

    private static final String rawDataFile = "C:\\Users\\sshah\\Desktop\\FF\\Data\\rawData.csv";
    private static final String csvFile = "C:\\Users\\sshah\\Desktop\\FF\\Data\\statsCSV.csv";
    private static final String excelFile = "C:\\Users\\sshah\\Desktop\\FF\\2017 Stats.xlsx";

    public static void main(String args[]) {

        //CSV to Objects
        List<Player> players = csvToObjects();

        //Calculate games played
        for (Player p : players) {
            p.calculateGamesPlayed();
        }

        //Add empty weeks
        for (Player p : players) {
            p.addEmptyWeeks(finalWeek);
        }

        //Calculate extra stats
        for (Player p : players) {
            p.calculateTrends();
        }

        //Dump all objects to CSV
        objectsToCSV(players);

        //Dump all objects to EXCEL
        objectsToXLSX(players);
    }

    private static List<Player> csvToObjects() {
        List<Player> players = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(rawDataFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
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

                    Week week = new Week(weekInfo, passingStats, receivingStats, rushingStats, miscStats);
                    player.addWeek(week);
                }

                players.add(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;
    }

    private static void objectsToCSV(List<Player> players) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(csvFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
            Writer writer = new BufferedWriter(outputStreamWriter);

            writer.write("ID,Player,");
            for (int i = 0; i < finalWeek; i++) {
                writer.write("Team,Position,Year,Week,Home,Atts,Comps,Comp %,Yards,Yards per Att,Yards per Comp,Ints,TDs,Two Pts,Two Pt Atts,");
                writer.write("Tgt,Rec,Rec %,Yards,Yards per Tgt,Yards per Rec,Yards after Catch,TDs,Two Pts,");
                writer.write("Carries,Yards,YPC,Yards after Catch,TDs,Two Pts,");
                writer.write("Fumbles,Fumbles Lost,Fantasy Pts,");
            }
            writer.write("passingAttsGm,passingCmpsGm,passingCmpPerGm,passingYardsGm,passingIntsGm,passingTDsGm,passingTwoPtsGm,passingTwoPtAttsGm,yardsPerAttGm,yardsPerCmpGm,receivingTargetsGm,receivingReceptionsGm,receivingYardsGm,receivingYardsAfterCatchGm,receivingTDsGm,receivingTwoPtsGm,catchPerGm,yardsPerTarGm,yardsPerCatGm,rushingAttemptsGm,rushingYardsGm,rushingYardsAfterContactGm,rushingTDsGm,rushingTwoPtsGm,yardsPerCarryGm,fumblesGm,fumblesLostGm,fantasyPtsGm");
            writer.write("attemptsTot,completionsTot,pYardsTot,interceptionsTot,pTdsTot,pTwoPtsTot,pTwoPtAttsTot,targetsTot,receptionsTot,rYardsTot,yardsAfterCatchTot,rTdsTot,rTwoPtsTot,carriesTot,ruYardsTot,yardsAfterContactTot,ruTdsTot,ruTwoPtsTot,fumblesTot,fumblesLostTot,fantasyPtsTotal\n");
            for (Player p : players) {
                writer.write(p.printCSV() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void objectsToXLSX(List<Player> players) {
        String headers[] = {"ID", "Player", "Team", "Position", "Year", "Week", "Home", "Atts", "Comps", "Comp %", "Yards", "Yards per Att", "Yards per Comp", "Ints", "TDs", "Two Pts", "Two Pt Atts", "Tgt", "Rec", "Rec %", "Yards", "Yards per Tgt", "Yards per Rec", "Yards after Catch", "TDs", "Two Pts", "Carries", "Yards", "YPC", "Yards after Catch", "TDs", "Two Pts", "Fumbles", "Fumbles Lost", "Fantasy Pts"};
        String totalHeaders[] = {"passingAttsGm", "passingCmpsGm", "passingCmpPerGm", "passingYardsGm", "passingIntsGm", "passingTDsGm", "passingTwoPtsGm", "passingTwoPtAttsGm", "yardsPerAttGm", "yardsPerCmpGm", "receivingTargetsGm", "receivingReceptionsGm", "receivingYardsGm", "receivingYardsAfterCatchGm", "receivingTDsGm", "receivingTwoPtsGm", "catchPerGm", "yardsPerTarGm", "yardsPerCatGm", "rushingAttemptsGm", "rushingYardsGm", "rushingYardsAfterContactGm", "rushingTDsGm", "rushingTwoPtsGm", "yardsPerCarryGm", "fumblesGm", "fumblesLostGm", "fantasyPtsGm"};
        String perGameHeaders[] = {"attemptsTot", "completionsTot", "pYardsTot", "interceptionsTot", "pTdsTot", "pTwoPtsTot", "pTwoPtAttsTot", "targetsTot", "receptionsTot", "rYardsTot", "yardsAfterCatchTot", "rTdsTot", "rTwoPtsTot", "carriesTot", "ruYardsTot", "yardsAfterContactTot", "ruTdsTot", "ruTwoPtsTot", "fumblesTot", "fumblesLostTot", "fantasyPtsTotal"};
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("All Stats");
            Row row = sheet.createRow(0);

            int i = 0;
            for (int j = 0; j < finalWeek; j++) {
                for (String s : headers) {
                    Cell cell = row.createCell(i++);
                    cell.setCellValue(s);
                }
            }

            for (String s : totalHeaders) {
                Cell cell = row.createCell(i++);
                cell.setCellValue(s);
            }

            for (String s : perGameHeaders) {
                Cell cell = row.createCell(i++);
                cell.setCellValue(s);
            }

            i = 1;
            for (Player p : players) {
                row = sheet.createRow(i++);
                int j = 0;
                Cell cell;

                cell = row.createCell(j++);
                cell.setCellValue(p.getId());
                cell = row.createCell(j++);
                cell.setCellValue(p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getWeekInfo().getTeam());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getWeekInfo().getPosition());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getWeekInfo().getYear());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getWeekInfo().getWeek());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getWeekInfo().isHome());

                    cell = row.createCell(j++);
                    cell.setCellValue(w.getPassingStats().getAttempts());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getPassingStats().getCompletions());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getPassingStats().getCmpPer());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getPassingStats().getYards());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getPassingStats().getYardsPerAtt());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getPassingStats().getYardsPerCmp());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getPassingStats().getInterceptions());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getPassingStats().getTds());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getPassingStats().getTwoPts());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getPassingStats().getTwoPtAtts());

                    cell = row.createCell(j++);
                    cell.setCellValue(w.getReceivingStats().getTargets());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getReceivingStats().getReceptions());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getReceivingStats().getRecPer());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getReceivingStats().getYards());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getReceivingStats().getYardsPerTar());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getReceivingStats().getYardsPerRec());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getReceivingStats().getYardsAfterCatch());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getReceivingStats().getTds());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getReceivingStats().getTwoPts());

                    cell = row.createCell(j++);
                    cell.setCellValue(w.getRushingStats().getCarries());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getRushingStats().getYards());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getRushingStats().getYardsPerCarry());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getRushingStats().getYardsAfterContact());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getRushingStats().getTds());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getRushingStats().getTwoPts());

                    cell = row.createCell(j++);
                    cell.setCellValue(w.getMiscStats().getFumbles());
                    cell = row.createCell(j++);
                    cell.setCellValue(w.getMiscStats().getFumblesLost());

                    cell = row.createCell(j++);
                    cell.setCellValue(w.getFantasyPts());
                }

                //cell = row.createCell(j++);
                //cell.setCellValue(p.getGamesPlayed());
                cell = row.createCell(j++);
                cell.setCellValue(p.getAttemptsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getCompletionsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getCmpPerGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getpYardsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getInterceptionsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getpTdsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getpTwoPtsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getpTwoPtAttsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getYardsPerAttGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getYardsPerCmpGm());

                cell = row.createCell(j++);
                cell.setCellValue(p.getTargetsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getReceptionsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getrYardsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getYardsAfterCatchGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getrTdsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getrTwoPtsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getRecPerGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getYardsPerTarGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getYardsPerRecGm());

                cell = row.createCell(j++);
                cell.setCellValue(p.getCarriesGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getRuYardsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getYardsAfterContactGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getRuTdsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getRuTwoPtsGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getYardsPerCarryGm());

                cell = row.createCell(j++);
                cell.setCellValue(p.getFumblesGm());
                cell = row.createCell(j++);
                cell.setCellValue(p.getFumblesLostGm());

                cell = row.createCell(j++);
                cell.setCellValue(p.getFantasyPtsGm());

                cell = row.createCell(j++);
                cell.setCellValue(p.getAttemptsTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getCompletionsTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getpYardsTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getInterceptionsTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getpTdsTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getpTwoPtsTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getpTwoPtAttsTot());

                cell = row.createCell(j++);
                cell.setCellValue(p.getTargetsTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getReceptionsTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getrYardsTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getYardsAfterCatchTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getrTdsTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getrTwoPtsTot());

                cell = row.createCell(j++);
                cell.setCellValue(p.getCarriesTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getRuYardsTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getYardsAfterContactTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getRuTdsTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getRuTwoPtsTot());

                cell = row.createCell(j++);
                cell.setCellValue(p.getFumblesTot());
                cell = row.createCell(j++);
                cell.setCellValue(p.getFumblesLostTot());

                cell = row.createCell(j);
                cell.setCellValue(p.getFantasyPtsTotal());
            }

            FileOutputStream fileOutputStream = new FileOutputStream(new File(excelFile));
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
