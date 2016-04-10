import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Developer on 07/03/2016.
 */
public class Microphone {
    private Lock lock;
    private Condition talk;
    private String speech;
    private boolean canItalk;
    private int nThread;
    private int reader;

    Microphone(int nThread) {
        lock = new ReentrantLock(false);
        talk = lock.newCondition();
        speech = null;
        canItalk = true;
        this.nThread = nThread;
        reader = 0;
    }

    public Lock getLock() {
        return lock;
    }

    public Condition getTalk() {
        return talk;
    }

    public boolean canITalk() {
        return canItalk;
    }

    public void setSpeech() {
        System.out.println(Color.ANSI_RED + Thread.currentThread() + " parlo" + Color.ANSI_RESET);
        speech = Thread.currentThread() + "";
        canItalk = false;
        nThread--;
    }

    public boolean iAmLast() {
        return reader == nThread;
    }

    public void reset() {
        reader = 0;
        canItalk = true;
        speech = null;
    }

    public void getSpeech() {
        reader++;
        System.out.println(Color.ANSI_YELLOW
                + Thread.currentThread() + ": " + speech + "R: " + reader + " T: " + nThread
                + Color.ANSI_RESET);

    }
}
