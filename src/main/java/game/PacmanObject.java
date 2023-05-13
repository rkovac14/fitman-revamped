package game;

import java.util.concurrent.ThreadLocalRandom;
import common.*;
import game.*;
import runner.ShowRunner;


/**
 * Holds the field in which pacman currently is
 */
public class PacmanObject implements MazeObject {
    //Field object
    private Field field;
    private boolean isPacman;
    private boolean hasKey = false;
    private CommonField.Direction direction;
    private final int colorID;

    /**
     * Initialize a field with PacMan
     * @param field location
     */
    public PacmanObject(Field field){
        this.field = field;
        colorID = ThreadLocalRandom.current().nextInt(1, 4 + 1);
    }

    //If possible, put the PacMan entity into a field. Switch based on directions.
    public CommonField.Direction move(CommonField.Direction dir) {
        Field thisField = PacmanMaze.map[this.field.getRow()][this.field.getCol()];
        Field nextField = null;
        if(this.direction == null)
            return null;
        if (this.direction == CommonField.Direction.U)
            nextField = PacmanMaze.map[this.field.getRow() - 1][this.field.getCol()];
        if (this.direction == CommonField.Direction.D)
            nextField = PacmanMaze.map[this.field.getRow() + 1][this.field.getCol()];
        if (this.direction == CommonField.Direction.L)
            nextField = PacmanMaze.map[this.field.getRow()][this.field.getCol() - 1];
        if (this.direction == CommonField.Direction.R)
            nextField = PacmanMaze.map[this.field.getRow()][this.field.getCol() + 1];
        if(!nextField.canMove())
            return null;
        if(!nextField.isEmpty()){
            if(this.isPacman && !nextField.get().isPacman())
                this.loseLife();
            if(!this.isPacman && nextField.get().isPacman())
                ((PacmanObject) nextField.get()).loseLife();
            if(!this.isPacman && !nextField.get().isPacman())
                return null;
        }
        nextField.put(this);
        thisField.remove(this);
        this.field = nextField;
        if(this.isPacman){
            if(((PathField)this.field).hasKey()){
                ((PathField)this.field).removeKey();
                this.hasKey = true;
                Sounds.playSound(Sounds.KEYSOUND);
            }
            if(((PathField)this.field).isTarget()){
                if(MazeConfigure.needKey) {
                    if (this.hasKey) {
                        ShowRunner.won = true;
                    } else {
                        System.out.println("You don't have key");
                    }
                }
            }
        }
        return this.direction;
    }
    @Override
    public CommonField getField() {
        return this.field;
    }
    @Override
    public int getColorID(){
        return this.colorID;
    }

    /**
     * If PacMan looses a life, the game ends
     */
    public void loseLife() {
        Sounds.playSound(5);
        try {
            Thread.sleep(2500);
        } catch (InterruptedException ignored) {
        }
        System.exit(0);
    }

    @Override
    public boolean isPacman() {
        return this.isPacman;
    }
    public void setPacman(boolean isPacman) {
        this.isPacman = isPacman;
    }
    public void changeDirection(CommonField.Direction dir){
        this.direction = dir;
    }
    public CommonField.Direction getDirection(){return this.direction;}

    /**
     * Calculate a direction to pacman field
     * @param pacmanField field with pacman
     */
    public void calculateDirection(PathField pacmanField){
        if(this.isPacman)
            return;
        CommonField.Direction oldDirection = this.direction;

        if(this.field.getCol() < pacmanField.getCol() && this.field.nextField(Field.Direction.R).canMove()){
            this.direction = CommonField.Direction.R;
            if(oldDirection == CommonField.Direction.L) {
                if (this.field.nextField(Field.Direction.U).canMove())
                    this.direction = CommonField.Direction.U;
                if (this.field.nextField(Field.Direction.D).canMove())
                    this.direction = CommonField.Direction.D;
            }
            return;
        }
        if(this.field.getCol() > pacmanField.getCol() && this.field.nextField(Field.Direction.L).canMove()){
            this.direction = CommonField.Direction.L;
            if(oldDirection == CommonField.Direction.R) {
                if (this.field.nextField(Field.Direction.D).canMove())
                    this.direction = CommonField.Direction.D;
                if (this.field.nextField(Field.Direction.U).canMove())
                    this.direction = CommonField.Direction.U;
            }
            return;
        }
        if(this.field.getRow() < pacmanField.getRow() && this.field.nextField(Field.Direction.D).canMove()){
            this.direction = CommonField.Direction.D;
            if(oldDirection == CommonField.Direction.U) {
                if (this.field.nextField(Field.Direction.L).canMove())
                    this.direction = CommonField.Direction.L;
                if (this.field.nextField(Field.Direction.R).canMove())
                    this.direction = CommonField.Direction.R;
            }
            return;
        }
        if(this.field.getRow() > pacmanField.getRow() && this.field.nextField(Field.Direction.U).canMove()){
            this.direction = CommonField.Direction.U;
            if(oldDirection == CommonField.Direction.D) {
                if (this.field.nextField(Field.Direction.R).canMove())
                    this.direction = CommonField.Direction.R;
                if (this.field.nextField(Field.Direction.L).canMove())
                    this.direction = CommonField.Direction.L;
            }
            return;
        }

        if(this.field.nextField(Field.Direction.R).canMove()){
            this.direction = CommonField.Direction.R;
            return;
        }

        if(this.field.nextField(Field.Direction.D).canMove()){
            this.direction = CommonField.Direction.D;
            return;
        }

        if(this.field.nextField(Field.Direction.L).canMove()){
            this.direction = CommonField.Direction.L;
            return;
        }

        if(this.field.nextField(Field.Direction.U).canMove()){
            this.direction = CommonField.Direction.U;
        }
    }
}
