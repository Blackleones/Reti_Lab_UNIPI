import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Developer on 07/03/2016.
 */
public class Buffer {
    private String[] buffer;
    private int size;
    private int csize;
    private Lock lock;
    private Condition full;
    private Condition empty;

    Buffer(int size) {
        this.size = size;
        buffer = new String[size];
        lock = new ReentrantLock(false);
        full = lock.newCondition();
        empty = lock.newCondition();
    }

    public Condition getFull() {
        return full;
    }

    public Condition getEmpty() {
        return empty;
    }

    public Lock getLock() {
        return lock;
    }

    public boolean isFull() {
        return csize + 1 == size;
    }

    public boolean isEmpty() {
        return csize == 0;
    }

    public void push(String s) {
        buffer[csize] = s;
        csize++;
    }

    public String pop() {
        String s = buffer[csize];
        buffer[csize] = null;
        csize--;
        return s;
    }
}
