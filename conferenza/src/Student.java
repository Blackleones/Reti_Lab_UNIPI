import java.util.Collection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by Developer on 07/03/2016.
 */
public class Student implements Runnable {
    private Microphone mic;
    private Lock lock;
    private Condition talk;

    Student (Microphone mic) {
        this.mic = mic;
        lock = mic.getLock();
        talk = mic.getTalk();
    }

    @Override
    public void run() {
        while(true) {
            lock.lock();
            try {
                if(mic.canITalk()) {
                    mic.setSpeech();
                    return;
                } else {
                    mic.getSpeech();

                    if(mic.iAmLast()){
                        mic.reset();
                        talk.signalAll();
                    }
                    else
                        talk.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
