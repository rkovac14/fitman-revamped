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
