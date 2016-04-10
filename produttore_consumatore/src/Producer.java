import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by Developer on 07/03/2016.
 */
public class Producer implements Runnable {
    private Buffer buffer;
    private Condition full;
    private Condition empty;
    private Lock lock;

    Producer(Buffer buffer) {
        this.buffer = buffer;
        this.full = buffer.getFull();
        this.empty = buffer.getEmpty();
        this.lock = buffer.getLock();
    }

    @Override
    public void run() {
        while(true) {
            lock.lock();
            try {
                if(!buffer.isFull()) {
                    buffer.push(Thread.currentThread()+"");
                    empty.signalAll();
                }
                else
                    full.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
