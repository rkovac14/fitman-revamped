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
