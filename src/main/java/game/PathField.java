/**
 * Path field
 * @author Richard Kovac (xkovac55)
 * @author Maros Sztolarik (xsztol00)
 */
package game;
import java.util.ArrayList;
import java.util.List;
import common.*;
import runner.ShowRunner;

/**
 * A field entities can move into
 */
public class PathField implements Field {
    private final int row;
    private final int col;
    private MazeObject workingObject = null;
    private final List<Observer> observersList;
    public boolean hasKey = false;
    private boolean isTarget = false;

    /**
     * Initialize a field as path
     * @param row row
     * @param col column
     */
    public PathField(int row, int col){
        this.observersList = new ArrayList<>();

        this.row = row;
        this.col = col;
    }
    //Simple equals override
    public boolean equals(Object obj){
        if (obj instanceof WallField){
            return false;
        }
        PathField o = (PathField) obj;
        return (this.row == o.row) && (this.col == o.col);
    }

    /**
     * Return neighboring field
     * @param dirs direction of movement
     * @return neighboring field
     */
    @Override
    public Field nextField(Direction dirs) {
        if(dirs == Field.Direction.U){ //UP
            return PacmanMaze.map[this.row-1][this.col];
        }
        if(dirs == Field.Direction.D){ //DOWN
            return PacmanMaze.map[this.row+1][this.col];
        }
        if(dirs == Field.Direction.R){ //RIGHT
            return PacmanMaze.map[this.row][this.col+1];
        }
        if(dirs == Field.Direction.L){ //LEFT
            return PacmanMaze.map[this.row][this.col-1];
        }
        return null;
    }

    //Self-explanatory
    @Override
    public MazeObject get() {
        return this.workingObject;
    }

    /**
     * Move in
     * @param object object moving in
     */
    @Override
    public void put(MazeObject object) {
        if(this.workingObject == null) {
            this.workingObject = object;
            notifyObservers();
        }
    }

    /**
     * Moving out
     * @param object object moving out
     */
    @Override
    public void remove(MazeObject object) {
        if(this.workingObject == null)
            return;
        this.workingObject = null;
        notifyObservers();
    }

    //Self-explanatory
    @Override
    public boolean isEmpty() {
        return this.workingObject == null;
    }
    //Self-explanatory
    @Override
    public boolean canMove() {
        return true;
    }

    public void putKey(){
        this.hasKey = true;
        this.notifyObservers();
    }

    public boolean hasKey(){
        return this.hasKey;
    }
    public void removeKey(){
        this.hasKey = false;
        if (ShowRunner.play == 1)
            Logger.write("Key removed\n" + this.row + "\n" + this.col + "\n");
        this.notifyObservers();
    }

    public void setTarget(){
        this.isTarget = true;
    }

    public boolean isTarget(){
        return this.isTarget;
    }

    //Self-explanatory
    @Override
    public int getCol(){
        return this.col;
    }
    //Self-explanatory
    @Override
    public int getRow(){
        return this.row;
    }
    @Override
    public void addObserver(Observer observer) {
        this.observersList.add(observer);
    }
    @Override
    public void notifyObservers() {
        this.observersList.forEach((o) -> o.update(this));
    }
}
