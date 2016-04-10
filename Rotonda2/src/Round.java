import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Developer on 11/03/2016.
 */
public class Round {
    public static String A = Color.ANSI_PURPLE + "funzione ";
    public static String B = "quad = " + Color.ANSI_RESET;
    public static boolean DEBUG = false;
    public static final int NQUAD = 4;
    private int nCar[];
    private Lock locks[];
    private Condition queues[];

    Round() {
        nCar = new int[NQUAD];
        locks = new Lock[NQUAD];
        queues = new Condition[NQUAD];

        for(int i = 0; i < NQUAD; i++) {
            locks[i] = new ReentrantLock(false);
            queues[i] = locks[i].newCondition();
        }
    }

    private int mod(int quad) {
        int q = Math.abs(quad) % NQUAD;
        return quad < 0 ? NQUAD - q : q;
    }

    //IP: quad = quadrante in cui la macchina è appena ANDATA
    public void moveTo(int quad) {
        quad = mod(quad);
        debug(A + "moveTo " + B + quad);
        nCar[quad]++;
        nCar[mod(quad - 1)]--;
    }

    //IP: quad = quadrante in cui la macchina si trova attualmente
    public void entry(int quad) {
        quad = mod(quad);
        debug(A + "entry " + B + quad);
        nCar[quad]++;
    }

    //IP: quad = quadrante in cui la macchina si trova attualmente
    public void exit(int quad) {
        quad = mod(quad);
        debug(A + "exit " + B + quad);
        nCar[quad]--;
    }

    public Lock getLockOf(int quad) {
        quad = mod(quad);
        debug(A + "getLockOf " + B + quad);
        return locks[quad];
    }

    public Condition getQueueOf(int quad) {
        quad = mod(quad);
        debug(A + "getQueueOf " + B + quad);
        return queues[quad];
    }

    public int getNCar(int quad) {
        quad = mod(quad);
        debug(A + "getNCar " + B + quad);
        return nCar[quad];
    }

    public void debug(String s) {
        if(DEBUG)System.out.println(s);
    }

    @Override
    public String toString() {
        String s = "[ "
                + nCar[0] + ", "
                + nCar[1] + ", "
                + nCar[2] + ", "
                + nCar[3] + " ]";
        return s;
    }
}
