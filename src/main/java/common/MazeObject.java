package common;

public interface MazeObject extends CommonMazeObject {
    CommonField.Direction move(CommonField.Direction dir);
}
