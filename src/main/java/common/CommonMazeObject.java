/**
 * CommonMazeObject Interface
 * @author Richard Kovac (xkovac55)
 * @author Maros Sztolarik (xsztol00)
 */
package common;

public interface CommonMazeObject {
    CommonField.Direction move(CommonField.Direction var1);
    default boolean isPacman() {
        return false;
    }
    int getColorID();
    CommonField getField();
    CommonField.Direction getDirection();
}
