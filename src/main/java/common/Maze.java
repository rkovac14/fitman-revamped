package common;

public interface Maze extends CommonMaze {
    Field getField(int row, int col);
    int numRows();
    int numCols();
}
