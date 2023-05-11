package common;

public interface Observable {
    void addObserver(Observer var1);
    void notifyObservers();
    interface Observer {
        void update(Observable var1);
    }
}
