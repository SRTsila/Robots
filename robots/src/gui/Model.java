package gui;

/**
 *
 */

public interface Model {
    public void attach(Observer observer);
    public void notifyAllObservers();
    public GameStatement getState();
}
