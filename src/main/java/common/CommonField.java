/**
 * CommonField Interface
 * @author Richard Kovac (xkovac55)
 * @author Maros Sztolarik (xsztol00)
 */
package common;
public interface CommonField extends Observable {
    boolean isEmpty();
    CommonMazeObject get();
    boolean canMove();
    enum Direction {
        L(),
        U(),
        R(),
        D();

        Direction() {
        }
    }
}
