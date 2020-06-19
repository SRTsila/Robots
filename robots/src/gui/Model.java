package gui;


public interface Model {
    void attach(Observer observer);

    void notifyAllObservers();

    GameStatement getState();
}
