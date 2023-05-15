/**
 * CommonMaze Interface
 * @author Richard Kovac (xkovac55)
 * @author Maros Sztolarik (xsztol00)
 */
package common;
import java.util.List;

public interface CommonMaze {
    CommonField getField(int var1, int var2);

    int numRows();

    int numCols();

    List<CommonMazeObject> ghosts();
}
