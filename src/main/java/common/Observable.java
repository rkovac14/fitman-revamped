/**
 * Observable Interface
 * @author Richard Kovac (xkovac55)
 * @author Maros Sztolarik (xsztol00)
 */
package common;
public interface Observable {
    void addObserver(Observer var1);
    void notifyObservers();
    interface Observer {
        void update(Observable var1);
    }
}
