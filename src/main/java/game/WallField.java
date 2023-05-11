package game;

import common.*;
import game.*;
import view.*;

import java.util.ArrayList;
import java.util.List;

/**
 * WallField class
 */
public class WallField implements Field{
    private final int row;
    private final int col;
    private final List<Observer> observersList;

    /**
     * Initialize field as wall
     * @param row row number
     * @param col col number
     */
    public WallField(int row, int col){
        this.observersList = new ArrayList<>();

        this.row = row;
        this.col = col;
    }
    //Simple equals override
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PathField){
            return false;
        }

        WallField o = (WallField) obj;
        return (this.row == o.row) && (this.col == o.col);
    }
    //Associate with maze
    @Override
    public Field nextField(Direction dirs) {
        return null;
    }

    //Well that cant be done ... instance of this is a wall
    @Override
    public void put(MazeObject object) {
        throw new UnsupportedOperationException("Well ... that did not work");
    }
    //Nothing to get ... from a wall
    @Override
    public MazeObject get() {
        return null;
    }
    //Nothing to remove ... from a wall
    @Override
    public void remove(MazeObject object) {
    }
    //Self-explanatory
    @Override
    public boolean isEmpty() {
        return true;
    }
    //Self-explanatory
    @Override
    public boolean canMove() {
        return false;
    }
    @Override
    public void putKey() {    }
    public void setTarget(){    }
    @Override
    public int getRow() {
        return this.row;
    }
    //Self-explanatory
    @Override
    public int getCol() {
        return this.col;
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
