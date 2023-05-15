/**
 * Field Interface
 * @author Richard Kovac (xkovac55)
 * @author Maros Sztolarik (xsztol00)
 */
package common;
public interface Field extends CommonField {
    enum Direction {D,L,R,U}
    Field nextField(Field.Direction dirs);
    void put(MazeObject object);
    void remove(MazeObject object);
    boolean isEmpty();
    //MazeObject get();
    boolean canMove();
    void putKey();
    void setTarget();
    int getRow();
    int getCol();
}
