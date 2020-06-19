package log;

import java.util.*;

public class LogQueue<T> implements Iterable<T> {
    private final LinkedList<T> queue;
    private final int maxSize;

    LogQueue(int maxSize) {
        this.maxSize = maxSize;
        queue = new LinkedList<>();
    }

    synchronized void add(T element) {
        if (!(queue.size() < maxSize)) {
            queue.poll();
        }
        queue.add(element);
    }

    synchronized int size() {
        return queue.size();
    }

    synchronized List<T> subList(int start, int finish) {
        return new ArrayList<>(queue.subList(start, finish));
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return queue.iterator();
    }
}
