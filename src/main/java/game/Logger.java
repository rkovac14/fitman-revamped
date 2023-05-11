package game;

import common.*;
import game.*;
import view.*;
import runner.ShowRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Logger {
    public static FileWriter logFile;
    public Logger(String filename){
        File file = new File(filename);
        try {
            logFile = new FileWriter(file);
        } catch (IOException e) {
            System.out.println("Problem with log file");
        }
    }

    /**
     * Write a map to log file
     * @param maze maze to write
     */
    public static void writeMap(PacmanMaze maze){
        write(maze.numRows()-2 + " ");
        write(maze.numCols()-2 + "\n");
        for(int i = 1; i < maze.numRows() - 1; i++){
            for(int j = 1; j < maze.numCols() - 1; j++){
                if(PacmanMaze.map[i][j] instanceof WallField) {
                    write("X");
                    continue;
                }
                if(((PathField) PacmanMaze.map[i][j]).hasKey()){
                    write("K");
                    continue;
                }
                if(!PacmanMaze.map[i][j].isEmpty() && ((PathField) PacmanMaze.map[i][j]).get().isPacman()){
                    write("S");
                    continue;
                }
                if(!PacmanMaze.map[i][j].isEmpty() && !((PathField) PacmanMaze.map[i][j]).get().isPacman()){
                    write("G");
                    continue;
                }
                if(((PathField) PacmanMaze.map[i][j]).isTarget()){
                    write("T");
                    continue;
                }
                if(PacmanMaze.map[i][j] instanceof PathField){
                    write(".");
                }
            }
            write(("\n"));
        }
    }
    public static void write(String input){
        try {
            logFile.write(input);
        } catch (IOException e) {
            System.out.print("Log file writing error");
        }
    }
    public static void writeDirection(CommonField.Direction dir){
        if (dir == CommonField.Direction.L) {
            Logger.write("L\n");
            return;
        }
        if (dir == CommonField.Direction.R){
            Logger.write("R\n");
            return;
        }
        if (dir == CommonField.Direction.D) {
            Logger.write("D\n");
            return;
        }
        if (dir == CommonField.Direction.U) {
            Logger.write("U\n");
        } else {
            Logger.write("\n");
        }
    }
}
