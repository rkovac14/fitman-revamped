package game;

import common.*;
import game.*;
import view.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class LogReader {
    private  static Scanner reader;
    public static final List<String> ghostMovements = new ArrayList<>();
    public static final List<String> pacmanMovements = new ArrayList<>();
    public static final List<Integer> keyRounds = new ArrayList<>();
    public static final List<Integer> keyRow = new ArrayList<>();
    public static final List<Integer> keyCol = new ArrayList<>();
    public LogReader(String filename){
        try {
            LogReader.reader = new Scanner(new File(filename));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Read a log file
     * @param numberOfGhosts Number of ghosts to track
     */
    public static void readLogFile(int numberOfGhosts){
        int round = -1;
        int ghostCounter = 0;
        while(reader.hasNextLine()){

            String line = reader.nextLine();
            if(line.equals("Round Start")) {
                //round start
                for(int i = 0; i < numberOfGhosts; i++){
                    ghostMovements.add(" ");
                }
                round++;
                ghostCounter = 0;
                continue;
            }
            if(line.charAt(0) == 'G'){
                //ghost is moving
                if(line.length() != 14){
                    ghostCounter++;
                    continue;
                }
                if(line.charAt(13) == 'R') {
                    ghostMovements.set(round * numberOfGhosts + ghostCounter, "R");
                    ghostCounter++;
                    continue;
                }
                if(line.charAt(13) == 'L') {
                    ghostMovements.set(round * numberOfGhosts + ghostCounter, "L");
                    ghostCounter++;
                    continue;
                }
                if(line.charAt(13) == 'D'){
                    ghostMovements.set(round * numberOfGhosts + ghostCounter, "D");
                    ghostCounter++;
                    continue;
                }
                if(line.charAt(13) == 'U'){
                    ghostMovements.set(round * numberOfGhosts + ghostCounter, "U");
                    ghostCounter++;
                    continue;
                }
            }

            //pacman is moving
            if(line.charAt(0) == 'P') {
                if(line.length() != 15){
                    pacmanMovements.add(" ");
                    continue;
                }
                if(line.charAt(14) == 'R'){
                    pacmanMovements.add("R");
                    continue;
                }
                if(line.charAt(14) == 'L'){
                    pacmanMovements.add("L");
                    continue;
                }
                if(line.charAt(14) == 'U'){
                    pacmanMovements.add("U");
                    continue;
                }
                if(line.charAt(14) == 'D'){
                    pacmanMovements.add("D");
                    continue;
                }
            }

            if(line.charAt(0) == 'K') {
                keyRounds.add(round);
                keyRow.add(Integer.parseInt(reader.nextLine()));
                keyCol.add(Integer.parseInt(reader.nextLine()));
            }
        }
    }
    public static CommonField.Direction directionParser(String direction){
        if(direction.equals("R"))
            return CommonField.Direction.R;
        if(direction.equals("L"))
            return CommonField.Direction.L;
        if(direction.equals("U"))
            return CommonField.Direction.U;
        if(direction.equals("D"))
            return CommonField.Direction.D;
        return null;
    }
    public static CommonField.Direction reverseDirectionParser(String direction){
        if(direction.equals("R"))
            return CommonField.Direction.L;
        if(direction.equals("L"))
            return CommonField.Direction.R;
        if(direction.equals("U"))
            return CommonField.Direction.D;
        if(direction.equals("D"))
            return CommonField.Direction.U;
        return null;
    }

}
