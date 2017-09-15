import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import stats.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Stats {

    private static final int finalWeek = 2;

    private static final String rawDataFile = "C:\\Users\\sshah\\Desktop\\FF\\Data\\rawData2017.csv";
    private static final String csvFile = "C:\\Users\\sshah\\Desktop\\FF\\Data\\2017statsCSV.csv";
    private static final String excelFile = "C:\\Users\\sshah\\Desktop\\FF\\2017 Stats.xlsx";

    public static void main(String args[]) {

        //CSV to Objects
        System.out.println("Converting CSV to objects");
        List<Player> players = csvToObjects();

        //Calculate games played
        System.out.println("Calculating games played");
        for (Player p : players) {
            p.calculateGamesPlayed();
        }

        //Add empty weeks
        System.out.println("Adding empty weeks");
        for (Player p : players) {
            p.addEmptyWeeks(finalWeek);
        }

        //Calculate extra stats
        System.out.println("Calculating player stats");
        for (Player p : players) {
            p.calculateTrends();
        }

        //Dump all objects to CSV
        System.out.println("Dumping all objects to CSV");
        objectsToCSV(players);

        //Dump all objects to EXCEL
        System.out.println("Dumping objects to EXCEL");
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
        Workbook workbook = new XSSFWorkbook();

        System.out.println("\tDumping all stats to ALL STATS");
        excelAllStats(players, workbook);

        System.out.println("\tDumping condensed stats to CONDENSED STATS");
        excelCondensedStats(players, workbook);

        System.out.println("\tDumping weekly fantasy totals to WEEKLY FANTASY POINTS");
        excelWeeklyFantasyPoints(players, workbook);

        System.out.println("\tDumping all QB stats to QB ALL STATS");
        excelAllQBStats(players, workbook);

        System.out.println("\tDumping QB condensed stats to QB CONDENSED STATS");
        excelCondensedQBStats(players, workbook);

        System.out.println("\tDumping QB weekly fantasy totals to QB WEEKLY FANTASY POINTS");
        excelWeeklyQBFantasyPoints(players, workbook);

        System.out.println("\tDumping all WR stats to WR ALL STATS");
        excelAllWRStats(players, workbook);

        System.out.println("\tDumping WR condensed stats to WR CONDENSED STATS");
        excelCondensedWRStats(players, workbook);

        System.out.println("\tDumping WR weekly fantasy totals to WR WEEKLY FANTASY POINTS");
        excelWeeklyWRFantasyPoints(players, workbook);

        System.out.println("\tDumping all RB stats to RB ALL STATS");
        excelAllRBStats(players, workbook);

        System.out.println("\tDumping RB condensed stats to RB CONDENSED STATS");
        excelCondensedRBStats(players, workbook);

        System.out.println("\tDumping RB weekly fantasy totals to RB WEEKLY FANTASY POINTS");
        excelWeeklyRBFantasyPoints(players, workbook);

        System.out.println("\tDumping all TE stats to TE ALL STATS");
        excelAllTEStats(players, workbook);

        System.out.println("\tDumping TE condensed stats to TE CONDENSED STATS");
        excelCondensedTEStats(players, workbook);

        System.out.println("\tDumping TE weekly fantasy totals to TE WEEKLY FANTASY POINTS");
        excelWeeklyTEFantasyPoints(players, workbook);

        System.out.println("\tDumping Weekly Stats to individual sheets");
        excelWeeklyStats(players, workbook);

        System.out.println("Closing EXCEL file");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(excelFile));
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void excelAllQBStats(List<Player> players, Workbook workbook) {
        String headers[] = {"ID", "Player"};
        String weeklyHeaders[] = {"Team", "Position", "Year", "Week", "Home", "Atts", "Comps", "Comp %", "Yards", "Yards per Att", "Yards per Comp", "Ints", "TDs", "Two Pts", "Two Pt Atts", "Fumbles", "Fumbles Lost", "Fantasy Pts"};
        String totalHeaders[] = {"passingAttsGm", "passingCmpsGm", "passingCmpPerGm", "passingYardsGm", "passingIntsGm", "passingTDsGm", "passingTwoPtsGm", "passingTwoPtAttsGm", "yardsPerAttGm", "yardsPerCmpGm", "fumblesGm", "fumblesLostGm", "fantasyPtsGm"};
        String perGameHeaders[] = {"attemptsTot", "completionsTot", "pYardsTot", "interceptionsTot", "pTdsTot", "pTwoPtsTot", "pTwoPtAttsTot", "fumblesTot", "fumblesLostTot", "fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("All QB Stats");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
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

        for (int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            if (p.getWeeks().get(0).getWeekInfo().getPosition().equals("QB")) {
                Row playerRow = sheet.createRow(i++);
                int j = 0;

                setCell(playerRow, j++, playerRowStyleInt, p.getId());
                setCell(playerRow, j++, playerRowStyleInt, p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getTeam());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getPosition());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getYear());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getWeek());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().isHome());

                    setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getAttempts());
                    setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getCompletions());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getPassingStats().getCmpPer());
                    setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getYards());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getPassingStats().getYardsPerAtt());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getPassingStats().getYardsPerCmp());
                    setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getInterceptions());
                    setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getTds());
                    setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getTwoPts());
                    setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getTwoPtAtts());

                    setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumbles());
                    setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumblesLost());

                    setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
                }

                setCell(playerRow, j++, playerRowStyleDouble, p.getAttemptsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getCompletionsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getCmpPerGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getpYardsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getInterceptionsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getpTdsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getpTwoPtsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getpTwoPtAttsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerAttGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerCmpGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesLostGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getAttemptsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getCompletionsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getpYardsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getInterceptionsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getpTdsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getpTwoPtsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getpTwoPtAttsTot());

                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesLostTot());

                setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
            }
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelCondensedQBStats(List<Player> players, Workbook workbook) {
        String headers[] = {"Player"};
        String weeklyHeaders[] = {"Team", "Position", "Week", "Home", "Atts", "Comps", "Yards", "Ints", "TDs", "Fumbles", "Fantasy Pts"};
        String totalHeaders[] = {"fantasyPtsGm"};
        String perGameHeaders[] = {"fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("Condensed QB Stats");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
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

        for(int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            if (p.getWeeks().get(0).getWeekInfo().getPosition().equals("QB")) {
                Row playerRow = sheet.createRow(i++);
                int j = 0;

                setCell(playerRow, j++, playerRowStyleInt, p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getTeam());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getPosition());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getWeek());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().isHome());

                    setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getAttempts());
                    setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getCompletions());
                    setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getYards());
                    setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getInterceptions());
                    setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getTds());

                    setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumbles());

                    setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
                }

                setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());
                setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
            }
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelWeeklyQBFantasyPoints(List<Player> players, Workbook workbook) {
        String headers[] = {"Player"};
        String weeklyHeaders[] = {"Fantasy Pts"};
        String totalHeaders[] = {"fantasyPtsGm"};
        String perGameHeaders[] = {"fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("Weekly QB Fantasy Points");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
                Cell cell = row.createCell(i++);
                cell.setCellValue("Week " + j + 1 + " " + s);
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

        for(int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            if (p.getWeeks().get(0).getWeekInfo().getPosition().equals("QB")) {
                Row playerRow = sheet.createRow(i++);
                int j = 0;

                setCell(playerRow, j++, playerRowStyleInt, p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
                }

                setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());
                setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
            }
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelAllWRStats(List<Player> players, Workbook workbook) {
        String headers[] = {"ID", "Player"};
        String weeklyHeaders[] = {"Team", "Position", "Year", "Week", "Home", "Tgt", "Rec", "Rec %", "Yards", "Yards per Tgt", "Yards per Rec", "Yards after Catch", "TDs", "Two Pts", "Fumbles", "Fumbles Lost", "Fantasy Pts"};
        String totalHeaders[] = {"receivingTargetsGm", "receivingReceptionsGm", "receivingYardsGm", "receivingYardsAfterCatchGm", "receivingTDsGm", "receivingTwoPtsGm", "catchPerGm", "yardsPerTarGm", "yardsPerCatGm", "fumblesGm", "fumblesLostGm", "fantasyPtsGm"};
        String perGameHeaders[] = {"targetsTot", "receptionsTot", "rYardsTot", "yardsAfterCatchTot", "rTdsTot", "rTwoPtsTot", "fumblesTot", "fumblesLostTot", "fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("All WR Stats");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
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

        for (int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            if (p.getWeeks().get(0).getWeekInfo().getPosition().equals("WR")) {
                Row playerRow = sheet.createRow(i++);
                int j = 0;

                setCell(playerRow, j++, playerRowStyleInt, p.getId());
                setCell(playerRow, j++, playerRowStyleInt, p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getTeam());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getPosition());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getYear());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getWeek());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().isHome());

                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTargets());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getReceptions());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getReceivingStats().getRecPer());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getYards());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getReceivingStats().getYardsPerTar());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getReceivingStats().getYardsPerRec());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getYardsAfterCatch());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTds());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTwoPts());

                    setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumbles());
                    setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumblesLost());

                    setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
                }

                setCell(playerRow, j++, playerRowStyleDouble, p.getTargetsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getReceptionsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrYardsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsAfterCatchGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrTdsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrTwoPtsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getRecPerGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerTarGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerRecGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesLostGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getTargetsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getReceptionsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrYardsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsAfterCatchTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrTdsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrTwoPtsTot());

                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesLostTot());

                setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
            }
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelCondensedWRStats(List<Player> players, Workbook workbook) {
        String headers[] = {"Player"};
        String weeklyHeaders[] = {"Team", "Position", "Week", "Home", "Tgt", "Rec", "Yards", "TDs", "Fumbles", "Fantasy Pts"};
        String totalHeaders[] = {"fantasyPtsGm"};
        String perGameHeaders[] = {"fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("Condensed WR Stats");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
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

        for(int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            if (p.getWeeks().get(0).getWeekInfo().getPosition().equals("WR")) {
                Row playerRow = sheet.createRow(i++);
                int j = 0;

                setCell(playerRow, j++, playerRowStyleInt, p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getTeam());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getPosition());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getWeek());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().isHome());

                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTargets());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getReceptions());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getYards());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTds());

                    setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumbles());

                    setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
                }

                setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());
                setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
            }
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelWeeklyWRFantasyPoints(List<Player> players, Workbook workbook) {
        String headers[] = {"Player"};
        String weeklyHeaders[] = {"Fantasy Pts"};
        String totalHeaders[] = {"fantasyPtsGm"};
        String perGameHeaders[] = {"fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("Weekly WR Fantasy Points");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
                Cell cell = row.createCell(i++);
                cell.setCellValue("Week " + j + 1 + " " + s);
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

        for(int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            if (p.getWeeks().get(0).getWeekInfo().getPosition().equals("WR")) {
                Row playerRow = sheet.createRow(i++);
                int j = 0;

                setCell(playerRow, j++, playerRowStyleInt, p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
                }

                setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());
                setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
            }
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelAllRBStats(List<Player> players, Workbook workbook) {
        String headers[] = {"ID", "Player"};
        String weeklyHeaders[] = {"Team", "Position", "Year", "Week", "Home", "Tgt", "Rec", "Rec %", "Yards", "Yards per Tgt", "Yards per Rec", "Yards after Catch", "TDs", "Two Pts", "Carries", "Yards", "YPC", "Yards after Catch", "TDs", "Two Pts", "Fumbles", "Fumbles Lost", "Fantasy Pts"};
        String totalHeaders[] = {"receivingTargetsGm", "receivingReceptionsGm", "receivingYardsGm", "receivingYardsAfterCatchGm", "receivingTDsGm", "receivingTwoPtsGm", "catchPerGm", "yardsPerTarGm", "yardsPerCatGm", "rushingAttemptsGm", "rushingYardsGm", "rushingYardsAfterContactGm", "rushingTDsGm", "rushingTwoPtsGm", "yardsPerCarryGm", "fumblesGm", "fumblesLostGm", "fantasyPtsGm"};
        String perGameHeaders[] = {"targetsTot", "receptionsTot", "rYardsTot", "yardsAfterCatchTot", "rTdsTot", "rTwoPtsTot", "carriesTot", "ruYardsTot", "yardsAfterContactTot", "ruTdsTot", "ruTwoPtsTot", "fumblesTot", "fumblesLostTot", "fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("All RB Stats");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
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

        for (int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            if (p.getWeeks().get(0).getWeekInfo().getPosition().equals("RB")) {
                Row playerRow = sheet.createRow(i++);
                int j = 0;

                setCell(playerRow, j++, playerRowStyleInt, p.getId());
                setCell(playerRow, j++, playerRowStyleInt, p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getTeam());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getPosition());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getYear());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getWeek());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().isHome());

                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTargets());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getReceptions());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getReceivingStats().getRecPer());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getYards());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getReceivingStats().getYardsPerTar());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getReceivingStats().getYardsPerRec());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getYardsAfterCatch());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTds());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTwoPts());

                    setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getCarries());
                    setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getYards());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getRushingStats().getYardsPerCarry());
                    setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getYardsAfterContact());
                    setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getTds());
                    setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getTwoPts());

                    setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumbles());
                    setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumblesLost());

                    setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
                }

                setCell(playerRow, j++, playerRowStyleDouble, p.getTargetsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getReceptionsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrYardsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsAfterCatchGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrTdsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrTwoPtsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getRecPerGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerTarGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerRecGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getCarriesGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getRuYardsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsAfterContactGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getRuTdsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getRuTwoPtsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerCarryGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesLostGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getTargetsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getReceptionsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrYardsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsAfterCatchTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrTdsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrTwoPtsTot());

                setCell(playerRow, j++, playerRowStyleDouble, p.getCarriesTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getRuYardsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsAfterContactTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getRuTdsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getRuTwoPtsTot());

                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesLostTot());

                setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
            }
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelCondensedRBStats(List<Player> players, Workbook workbook) {
        String headers[] = {"Player"};
        String weeklyHeaders[] = {"Team", "Position", "Week", "Home", "Tgt", "Rec", "Yards", "TDs", "Carries", "Yards", "TDs", "Fumbles", "Fantasy Pts"};
        String totalHeaders[] = {"fantasyPtsGm"};
        String perGameHeaders[] = {"fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("Condensed RB Stats");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
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

        for(int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            if (p.getWeeks().get(0).getWeekInfo().getPosition().equals("RB")) {
                Row playerRow = sheet.createRow(i++);
                int j = 0;

                setCell(playerRow, j++, playerRowStyleInt, p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getTeam());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getPosition());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getWeek());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().isHome());

                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTargets());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getReceptions());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getYards());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTds());

                    setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getCarries());
                    setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getYards());
                    setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getTds());

                    setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumbles());

                    setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
                }

                setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());
                setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
            }
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelWeeklyRBFantasyPoints(List<Player> players, Workbook workbook) {
        String headers[] = {"Player"};
        String weeklyHeaders[] = {"Fantasy Pts"};
        String totalHeaders[] = {"fantasyPtsGm"};
        String perGameHeaders[] = {"fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("Weekly RB Fantasy Points");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
                Cell cell = row.createCell(i++);
                cell.setCellValue("Week " + j + 1 + " " + s);
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

        for(int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            if (p.getWeeks().get(0).getWeekInfo().getPosition().equals("RB")) {
                Row playerRow = sheet.createRow(i++);
                int j = 0;

                setCell(playerRow, j++, playerRowStyleInt, p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
                }

                setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());
                setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
            }
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelAllTEStats(List<Player> players, Workbook workbook) {
        String headers[] = {"ID", "Player"};
        String weeklyHeaders[] = {"Team", "Position", "Year", "Week", "Home", "Tgt", "Rec", "Rec %", "Yards", "Yards per Tgt", "Yards per Rec", "Yards after Catch", "TDs", "Two Pts", "Fumbles", "Fumbles Lost", "Fantasy Pts"};
        String totalHeaders[] = {"receivingTargetsGm", "receivingReceptionsGm", "receivingYardsGm", "receivingYardsAfterCatchGm", "receivingTDsGm", "receivingTwoPtsGm", "catchPerGm", "yardsPerTarGm", "yardsPerCatGm", "fumblesGm", "fumblesLostGm", "fantasyPtsGm"};
        String perGameHeaders[] = {"targetsTot", "receptionsTot", "rYardsTot", "yardsAfterCatchTot", "rTdsTot", "rTwoPtsTot", "fumblesTot", "fumblesLostTot", "fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("All TE Stats");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
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

        for (int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            if (p.getWeeks().get(0).getWeekInfo().getPosition().equals("TE")) {
                Row playerRow = sheet.createRow(i++);
                int j = 0;

                setCell(playerRow, j++, playerRowStyleInt, p.getId());
                setCell(playerRow, j++, playerRowStyleInt, p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getTeam());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getPosition());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getYear());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getWeek());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().isHome());

                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTargets());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getReceptions());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getReceivingStats().getRecPer());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getYards());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getReceivingStats().getYardsPerTar());
                    setCell(playerRow, j++, playerRowStyleDouble, w.getReceivingStats().getYardsPerRec());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getYardsAfterCatch());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTds());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTwoPts());

                    setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumbles());
                    setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumblesLost());

                    setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
                }

                setCell(playerRow, j++, playerRowStyleDouble, p.getTargetsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getReceptionsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrYardsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsAfterCatchGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrTdsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrTwoPtsGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getRecPerGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerTarGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerRecGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesGm());
                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesLostGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());

                setCell(playerRow, j++, playerRowStyleDouble, p.getTargetsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getReceptionsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrYardsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getYardsAfterCatchTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrTdsTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getrTwoPtsTot());

                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesTot());
                setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesLostTot());

                setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
            }
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelCondensedTEStats(List<Player> players, Workbook workbook) {
        String headers[] = {"Player"};
        String weeklyHeaders[] = {"Team", "Position", "Week", "Home", "Tgt", "Rec", "Yards", "TDs", "Fumbles", "Fantasy Pts"};
        String totalHeaders[] = {"fantasyPtsGm"};
        String perGameHeaders[] = {"fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("Condensed TE Stats");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
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

        for(int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            if (p.getWeeks().get(0).getWeekInfo().getPosition().equals("TE")) {
                Row playerRow = sheet.createRow(i++);
                int j = 0;

                setCell(playerRow, j++, playerRowStyleInt, p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getTeam());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getPosition());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getWeek());
                    setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().isHome());

                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTargets());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getReceptions());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getYards());
                    setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTds());

                    setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumbles());

                    setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
                }


                setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());
                setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
            }
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelWeeklyTEFantasyPoints(List<Player> players, Workbook workbook) {
        String headers[] = {"Player"};
        String weeklyHeaders[] = {"Fantasy Pts"};
        String totalHeaders[] = {"fantasyPtsGm"};
        String perGameHeaders[] = {"fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("Weekly TE Fantasy Points");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
                Cell cell = row.createCell(i++);
                cell.setCellValue("Week " + j + 1 + " " + s);
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

        for(int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            if (p.getWeeks().get(0).getWeekInfo().getPosition().equals("TE")) {
                Row playerRow = sheet.createRow(i++);
                int j = 0;

                setCell(playerRow, j++, playerRowStyleInt, p.getName());

                List<Week> weeks = p.getWeeks();
                for (Week w : weeks) {
                    setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
                }

                setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());
                setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
            }
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelAllStats(List<Player> players, Workbook workbook) {
        String headers[] = {"ID", "Player"};
        String weeklyHeaders[] = {"Team", "Position", "Year", "Week", "Home", "Atts", "Comps", "Comp %", "Yards", "Yards per Att", "Yards per Comp", "Ints", "TDs", "Two Pts", "Two Pt Atts", "Tgt", "Rec", "Rec %", "Yards", "Yards per Tgt", "Yards per Rec", "Yards after Catch", "TDs", "Two Pts", "Carries", "Yards", "YPC", "Yards after Catch", "TDs", "Two Pts", "Fumbles", "Fumbles Lost", "Fantasy Pts"};
        String totalHeaders[] = {"passingAttsGm", "passingCmpsGm", "passingCmpPerGm", "passingYardsGm", "passingIntsGm", "passingTDsGm", "passingTwoPtsGm", "passingTwoPtAttsGm", "yardsPerAttGm", "yardsPerCmpGm", "receivingTargetsGm", "receivingReceptionsGm", "receivingYardsGm", "receivingYardsAfterCatchGm", "receivingTDsGm", "receivingTwoPtsGm", "catchPerGm", "yardsPerTarGm", "yardsPerCatGm", "rushingAttemptsGm", "rushingYardsGm", "rushingYardsAfterContactGm", "rushingTDsGm", "rushingTwoPtsGm", "yardsPerCarryGm", "fumblesGm", "fumblesLostGm", "fantasyPtsGm"};
        String perGameHeaders[] = {"attemptsTot", "completionsTot", "pYardsTot", "interceptionsTot", "pTdsTot", "pTwoPtsTot", "pTwoPtAttsTot", "targetsTot", "receptionsTot", "rYardsTot", "yardsAfterCatchTot", "rTdsTot", "rTwoPtsTot", "carriesTot", "ruYardsTot", "yardsAfterContactTot", "ruTdsTot", "ruTwoPtsTot", "fumblesTot", "fumblesLostTot", "fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("All Stats");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
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

        for (int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            Row playerRow = sheet.createRow(i++);
            int j = 0;

            setCell(playerRow, j++, playerRowStyleInt, p.getId());
            setCell(playerRow, j++, playerRowStyleInt, p.getName());

            List<Week> weeks = p.getWeeks();
            for (Week w : weeks) {
                setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getTeam());
                setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getPosition());
                setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getYear());
                setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getWeek());
                setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().isHome());

                setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getAttempts());
                setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getCompletions());
                setCell(playerRow, j++, playerRowStyleDouble, w.getPassingStats().getCmpPer());

                setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getYards());
                setCell(playerRow, j++, playerRowStyleDouble, w.getPassingStats().getYardsPerAtt());
                setCell(playerRow, j++, playerRowStyleDouble, w.getPassingStats().getYardsPerCmp());
                setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getInterceptions());
                setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getTds());
                setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getTwoPts());
                setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getTwoPtAtts());

                setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTargets());
                setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getReceptions());
                setCell(playerRow, j++, playerRowStyleDouble, w.getReceivingStats().getRecPer());
                setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getYards());
                setCell(playerRow, j++, playerRowStyleDouble, w.getReceivingStats().getYardsPerTar());
                setCell(playerRow, j++, playerRowStyleDouble, w.getReceivingStats().getYardsPerRec());
                setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getYardsAfterCatch());
                setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTds());
                setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTwoPts());

                setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getCarries());
                setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getYards());
                setCell(playerRow, j++, playerRowStyleDouble, w.getRushingStats().getYardsPerCarry());
                setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getYardsAfterContact());
                setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getTds());
                setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getTwoPts());

                setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumbles());
                setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumblesLost());

                setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
            }

            setCell(playerRow, j++, playerRowStyleDouble, p.getAttemptsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getCompletionsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getCmpPerGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getpYardsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getInterceptionsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getpTdsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getpTwoPtsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getpTwoPtAttsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerAttGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerCmpGm());

            setCell(playerRow, j++, playerRowStyleDouble, p.getTargetsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getReceptionsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getrYardsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getYardsAfterCatchGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getrTdsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getrTwoPtsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getRecPerGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerTarGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerRecGm());

            setCell(playerRow, j++, playerRowStyleDouble, p.getCarriesGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getRuYardsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getYardsAfterContactGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getRuTdsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getRuTwoPtsGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getYardsPerCarryGm());

            setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesGm());
            setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesLostGm());

            setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());

            setCell(playerRow, j++, playerRowStyleDouble, p.getAttemptsTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getCompletionsTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getpYardsTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getInterceptionsTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getpTdsTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getpTwoPtsTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getpTwoPtAttsTot());

            setCell(playerRow, j++, playerRowStyleDouble, p.getTargetsTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getReceptionsTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getrYardsTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getYardsAfterCatchTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getrTdsTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getrTwoPtsTot());

            setCell(playerRow, j++, playerRowStyleDouble, p.getCarriesTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getRuYardsTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getYardsAfterContactTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getRuTdsTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getRuTwoPtsTot());

            setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesTot());
            setCell(playerRow, j++, playerRowStyleDouble, p.getFumblesLostTot());

            setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelCondensedStats(List<Player> players, Workbook workbook) {
        String headers[] = {"Player"};
        String weeklyHeaders[] = {"Team", "Position", "Week", "Home", "Atts", "Comps", "Yards", "Ints", "TDs", "Tgt", "Rec", "Yards", "TDs", "Carries", "Yards", "TDs", "Fumbles", "Fantasy Pts"};
        String totalHeaders[] = {"fantasyPtsGm"};
        String perGameHeaders[] = {"fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("Condensed Stats");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
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

        for(int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            Row playerRow = sheet.createRow(i++);
            int j = 0;

            setCell(playerRow, j++, playerRowStyleInt, p.getName());

            List<Week> weeks = p.getWeeks();
            for (Week w : weeks) {
                setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getTeam());
                setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getPosition());
                setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().getWeek());
                setCell(playerRow, j++, playerRowStyleInt, w.getWeekInfo().isHome());

                setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getAttempts());
                setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getCompletions());
                setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getYards());
                setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getInterceptions());
                setCell(playerRow, j++, playerRowStyleInt, w.getPassingStats().getTds());

                setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTargets());
                setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getReceptions());
                setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getYards());
                setCell(playerRow, j++, playerRowStyleInt, w.getReceivingStats().getTds());

                setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getCarries());
                setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getYards());
                setCell(playerRow, j++, playerRowStyleInt, w.getRushingStats().getTds());

                setCell(playerRow, j++, playerRowStyleInt, w.getMiscStats().getFumbles());

                setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
            }

            setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());
            setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelWeeklyFantasyPoints(List<Player> players, Workbook workbook) {
        String headers[] = {"Player"};
        String weeklyHeaders[] = {"Fantasy Pts"};
        String totalHeaders[] = {"fantasyPtsGm"};
        String perGameHeaders[] = {"fantasyPtsTotal"};

        Sheet sheet = workbook.createSheet("Weekly Fantasy Points");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        for (String s : headers) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(s);
        }

        for (int j = 0; j < finalWeek; j++) {
            for (String s : weeklyHeaders) {
                Cell cell = row.createCell(i++);
                cell.setCellValue("Week " + j + 1 + " " + s);
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

        for(int j = 0; j < row.getLastCellNum(); j++) {
            row.getCell(j).setCellStyle(cellStyle);
        }

        CellStyle playerRowStyleInt = workbook.createCellStyle();
        playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

        CellStyle playerRowStyleDouble = workbook.createCellStyle();
        playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
        playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        i = 1;
        for (Player p : players) {
            Row playerRow = sheet.createRow(i++);
            int j = 0;

            setCell(playerRow, j++, playerRowStyleInt, p.getName());

            List<Week> weeks = p.getWeeks();
            for (Week w : weeks) {
                setCell(playerRow, j++, playerRowStyleDouble, w.getFantasyPts());
            }

            setCell(playerRow, j++, playerRowStyleDouble, p.getFantasyPtsGm());
            setCell(playerRow, j, playerRowStyleDouble, p.getFantasyPtsTotal());
        }

        for (int j = 0; j < row.getLastCellNum(); j++) {
            sheet.autoSizeColumn(j);
        }
    }

    private static void excelWeeklyStats(List<Player> players, Workbook workbook) {
        String weeklyHeaders[] = {"ID", "Player", "Team", "Position", "Year", "Week", "Home", "Atts", "Comps", "Comp %", "Yards", "Yards per Att", "Yards per Comp", "Ints", "TDs", "Two Pts", "Two Pt Atts", "Tgt", "Rec", "Rec %", "Yards", "Yards per Tgt", "Yards per Rec", "Yards after Catch", "TDs", "Two Pts", "Carries", "Yards", "YPC", "Yards after Catch", "TDs", "Two Pts", "Fumbles", "Fumbles Lost", "Fantasy Pts"};

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setRotation((short) 90);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        for (int i = 1; i <= finalWeek; i++) {
            Sheet sheet = workbook.createSheet("Week " + i + " Stats");
            Row row = sheet.createRow(0);

            int j = 0;
            for (String s : weeklyHeaders) {
                Cell cell = row.createCell(j++);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(s);
            }

            CellStyle playerRowStyleInt = workbook.createCellStyle();
            playerRowStyleInt.setAlignment(HorizontalAlignment.LEFT);

            CellStyle playerRowStyleDouble = workbook.createCellStyle();
            playerRowStyleDouble.setAlignment(HorizontalAlignment.LEFT);
            playerRowStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

            int curRow = 1;
            for (Player p : players) {
                Row playerRow = sheet.createRow(curRow++);
                int curCol = 0;

                setCell(playerRow, curCol++, playerRowStyleInt, p.getId());
                setCell(playerRow, curCol++, playerRowStyleInt, p.getName());

                Week w = p.getWeeks().get(i - 1);
                setCell(playerRow, curCol++, playerRowStyleInt, w.getWeekInfo().getTeam());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getWeekInfo().getPosition());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getWeekInfo().getYear());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getWeekInfo().getWeek());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getWeekInfo().isHome());

                setCell(playerRow, curCol++, playerRowStyleInt, w.getPassingStats().getAttempts());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getPassingStats().getCompletions());
                setCell(playerRow, curCol++, playerRowStyleDouble, w.getPassingStats().getCmpPer());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getPassingStats().getYards());
                setCell(playerRow, curCol++, playerRowStyleDouble, w.getPassingStats().getYardsPerAtt());
                setCell(playerRow, curCol++, playerRowStyleDouble, w.getPassingStats().getYardsPerCmp());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getPassingStats().getInterceptions());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getPassingStats().getTds());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getPassingStats().getTwoPts());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getPassingStats().getTwoPtAtts());

                setCell(playerRow, curCol++, playerRowStyleInt, w.getReceivingStats().getTargets());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getReceivingStats().getReceptions());
                setCell(playerRow, curCol++, playerRowStyleDouble, w.getReceivingStats().getRecPer());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getReceivingStats().getYards());
                setCell(playerRow, curCol++, playerRowStyleDouble, w.getReceivingStats().getYardsPerTar());
                setCell(playerRow, curCol++, playerRowStyleDouble, w.getReceivingStats().getYardsPerRec());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getReceivingStats().getYardsAfterCatch());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getReceivingStats().getTds());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getReceivingStats().getTwoPts());

                setCell(playerRow, curCol++, playerRowStyleInt, w.getRushingStats().getCarries());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getRushingStats().getYards());
                setCell(playerRow, curCol++, playerRowStyleDouble, w.getRushingStats().getYardsPerCarry());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getRushingStats().getYardsAfterContact());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getRushingStats().getTds());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getRushingStats().getTwoPts());

                setCell(playerRow, curCol++, playerRowStyleInt, w.getMiscStats().getFumbles());
                setCell(playerRow, curCol++, playerRowStyleInt, w.getMiscStats().getFumblesLost());

                setCell(playerRow, curCol, playerRowStyleDouble, w.getFantasyPts());
            }

            for (int s = 0; s < row.getLastCellNum(); s++) {
                sheet.autoSizeColumn(s);
            }
        }
    }

    private static void setCell(Row row, int rowNum, CellStyle cellStyle, String value) {
        Cell cell = row.createCell(rowNum);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }

    private static void setCell(Row row, int rowNum, CellStyle cellStyle, int value) {
        Cell cell = row.createCell(rowNum);
        cell.setCellStyle(cellStyle);
        cell.setCellType(CellType.NUMERIC);
        cell.setCellValue(value);
    }

    private static void setCell(Row row, int rowNum, CellStyle cellStyle, double value) {
        Cell cell = row.createCell(rowNum);
        cell.setCellStyle(cellStyle);
        cell.setCellType(CellType.NUMERIC);
        if (Double.isNaN(value) || Double.isInfinite(value))
            cell.setCellValue(0.00);
        else
            cell.setCellValue(value);
    }

    private static void setCell(Row row, int rowNum, CellStyle cellStyle, boolean value) {
        Cell cell = row.createCell(rowNum);
        cell.setCellStyle(cellStyle);
        cell.setCellType(CellType.BOOLEAN);
        cell.setCellValue(value);
    }
}
