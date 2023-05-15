/**
 * Maze Interface
 * @author Richard Kovac (xkovac55)
 * @author Maros Sztolarik (xsztol00)
 */
package common;
public interface Maze extends CommonMaze {
    Field getField(int row, int col);
    int numRows();
    int numCols();
}
