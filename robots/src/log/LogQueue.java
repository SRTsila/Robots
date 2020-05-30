package log;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogQueue<T> implements Iterable{
    private Queue<T> queue;
    private final int maxSize;

    LogQueue(int maxSize) {
        this.maxSize = maxSize;
        queue = new ConcurrentLinkedQueue<T>();
    }

    void add(T element) {
        if (!(queue.size() < maxSize)) {
            queue.poll();
        }
        queue.add(element);
    }

    int size() {
        return queue.size();
    }

    List<T> subList(int start, int finish) {
        ArrayList<T> list = toArray();
        return list.subList(start, finish);
    }

    private ArrayList<T> toArray() {
        ArrayList<T> result = new ArrayList<>();
        Queue<T> currentQueue = queue;
        while (true) {
            try {
                result.add(currentQueue.remove());
            } catch (NoSuchElementException e) {
                break;
            }

        }
        return result;
    }

    @Override
    public Iterator iterator() {
        return queue.iterator();
    }
}
