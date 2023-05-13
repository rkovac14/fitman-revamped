package game;

import common.*;
import game.*;

public class MazeConfigure {
    private int cols = 0; //Map columns variable
    private int rows = 0; //Map rows variable
    public static boolean needKey = false;
    private char[][] map; //Map keeping variable
    private int currRow = 1; //Processing started at first row (0 is wall)
    private boolean wrongLine = false; //Triggered by a wrong sized line

    /**
     * Read a map and start building it
     * @param rows number of rows
     * @param cols number of cols
     */
    public void startReading(int rows, int cols){
        // Adding walls ...
        this.cols = cols+2;
        this.rows = rows+2;
        this.map = new char[this.rows][this.cols];
        // Building walls ...
        for(int i = 0; i<this.rows; i++){
            for(int j = 0; j<this.cols; j++){
                //First and last "blocks" are set as walls
                if(i == 0 || i == this.rows-1) {
                    this.map[i][j] = 'X';
                    continue;
                }
                if(j == 0 || j == this.cols-1) {
                    this.map[i][j] = 'X';
                }
            }

        }
    }

    /**
     * Check line validity
     *
     * @param line line to check
     */
    public void processLine(String line){
        //Wrong line length check
        if(line.length() != this.cols-2) {
            wrongLine = true;
            return;
        }
        // Input char -> map
        for(int i=1; i<=line.length(); i++){
            map[currRow][i] = line.charAt(i-1);
        }
        currRow += 1;
    }

    /**
     * Build a maze
     * @return built maze
     */
    public Maze createMaze(){
        //Self-explanatory
        if(wrongLine)
            return null;

        //Now we reconstruct the map - by processing chars into fields
        Field[][] field = new Field[this.rows][this.cols];
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                if(map[i][j] == '.'){
                    field[i][j] = new PathField(i,j);
                    continue;
                }
                if(map[i][j] == 'X'){
                    field[i][j] = new WallField(i,j);
                    continue;
                }
                if(map[i][j] == 'S'){
                    field[i][j] = new PathField(i,j);
                    PacmanObject pacman = new PacmanObject(field[i][j]);
                    pacman.setPacman(true);
                    field[i][j].put(pacman);
                    continue;
                }
                if(map[i][j] == 'G'){
                    field[i][j] = new PathField(i,j);
                    PacmanObject ghost = new PacmanObject(field[i][j]);
                    ghost.setPacman(false);
                    field[i][j].put(ghost);
                    continue;
                }
                if(map[i][j] == 'T'){
                    field[i][j] = new PathField(i,j);
                    field[i][j].setTarget();
                    continue;
                }
                if(map[i][j] == 'K'){
                    field[i][j] = new PathField(i,j);
                    field[i][j].putKey();
                    MazeConfigure.needKey = true;

                }
            }
        }
        PacmanMaze maze = new PacmanMaze(this.rows, this.cols);
        PacmanMaze.create(field);
        return maze;
    }
}
