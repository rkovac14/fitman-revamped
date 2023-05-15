/**
 * MazeObject Interface
 * @author Richard Kovac (xkovac55)
 * @author Maros Sztolarik (xsztol00)
 */
package common;
public interface MazeObject extends CommonMazeObject {
    CommonField.Direction move(CommonField.Direction dir);
}
