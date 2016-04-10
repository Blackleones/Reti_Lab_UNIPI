import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by Developer on 07/03/2016.
 */
public class Consumer implements Runnable {
    private Buffer buffer;
    private Condition full;
    private Condition empty;
    private Lock lock;

    Consumer(Buffer buffer) {
        this.buffer = buffer;
        full = buffer.getFull();
        empty = buffer.getEmpty();
        lock = buffer.getLock();
    }

    @Override
    public void run() {
        while(true) {
            lock.lock();
            try {
                if(!buffer.isEmpty()) {
                    System.out.println(Color.ANSI_GREEN +
                            "C " + Thread.currentThread() + " " + buffer.pop()
                            + Color.ANSI_RESET);
                    full.signalAll();
                }
                else
                    empty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
