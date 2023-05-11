package game;

import java.util.ArrayList;
import java.util.List;

import common.*;
import game.*;
import view.*;
/**
 * Holds the map (maze)
 */
public class PacmanMaze implements Maze {
    //Self explanatory ...
    private List<CommonMazeObject> ghosts;
    private final int rows;
    private final int cols;
    public static Field[][] map;
    private boolean flip = false;

    /**
     * Initialize a maze
     * @param rows size in rows
     * @param cols size in cols
     */
    public PacmanMaze(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Copy a maze
     *
     * @param board Given maze
     */
    public static void create(Field[][] board){
        PacmanMaze.map = board;
    }

    /**
     * Return a maze field
     * @param row what row
     * @param col what col
     * @return selected field
     */
    public Field getField(int row, int col){
        return PacmanMaze.map[row][col];
    }

    public int numRows(){
        return this.rows;
    }

    public int numCols(){
        return this.cols;
    }

    /**
     * Return ghost locations
     * @return list of CommonMazeObjects
     */
    @Override
    public List<CommonMazeObject> ghosts() {
        if(!flip) {
            this.ghosts = new ArrayList<>();
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    if (map[i][j].get() != null && !map[i][j].get().isPacman()) {
                        this.ghosts.add(map[i][j].get());
                    }
                }
            }
            this.flip = true;
        }
        return new ArrayList<>(this.ghosts);
    }

    /**
     * Return PacMan location
     * @return pacman location (object he is in)
     */
    public CommonMazeObject getPacman() {
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                if(map[i][j].get() != null && map[i][j].get().isPacman()){
                    return map[i][j].get();
                }
            }
        }
        return null;
    }

}
