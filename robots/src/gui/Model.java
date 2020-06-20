package gui;

/**
 * Интерфейс обозреваемого объекта, который информирует своих подписчиков об обновлениях.
 */

public interface Model {

    void attach(Observer observer);

    void notifyAllObservers();

    GameStatement getState();
}

