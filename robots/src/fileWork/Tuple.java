package fileWork;

/**
 * Класс Кортеж - анлог кортежа из с#. Урезанная версия.
 */

public class Tuple<T1, T2> {
    private T1 first;
    private T2 second;

    public Tuple(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    T1 getFirst() {
        return first;
    }

    T2 getSecond() {
        return second;
    }
}
