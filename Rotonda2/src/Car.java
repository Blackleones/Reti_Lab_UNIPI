import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Developer on 11/03/2016.
 */
public class Car implements Runnable {
    private static final String A = Color.ANSI_RED + ": SX occupata, mi metto in attesa" + Color.ANSI_RESET;
    private static final String B = Color.ANSI_GREEN + ": SX libera, entro nel quadrante " + Color.ANSI_RESET;
    private static final String C = Color.ANSI_YELLOW + ": Devo ancora entrare nella rotonda" + Color.ANSI_RESET;
    private static final String D = Color.ANSI_YELLOW + ": entry = " + Color.ANSI_RESET;
    private static final String E = Color.ANSI_YELLOW + " exit = " + Color.ANSI_RESET;
    private static final String F = Color.ANSI_GREEN + ": fine." + Color.ANSI_RESET;
    private static final String G = Color.ANSI_BLUE + ": devo muovermi" + Color.ANSI_RESET;
    private static final String H = Color.ANSI_BLUE + ": in movimento" + Color.ANSI_RESET;

    private static boolean DEBUG = true;
    private final int entry; //quadrante d'ingresso
    private final int exit; //quadrante d'uscita
    private int myQuad;
    private static Round round;
    private boolean insideRound;
    private boolean iAmAlive;

    Car(Round round) {
        this.round = round;
        insideRound = false;
        iAmAlive = true;
        entry = ThreadLocalRandom.current().nextInt(4);
        exit =  ThreadLocalRandom.current().nextInt(4);
        myQuad = -1; //all'inizio non sono dentro un quadrante
    }

    @Override
    public void run() {
        while(iAmAlive) {
            if (!insideRound) {
                debug(Thread.currentThread() + C + " " + round);
                debug(Thread.currentThread() + D + entry + E + exit);
                round.getLockOf(entry - 1).lock();
                try {
                    //se non ho la precedenza a SX attendo
                    while (round.getNCar(entry - 1) != 0) {
                        debug(Thread.currentThread() + A + D + entry + " " + round);
                        //mi metto in attesa sulla SX
                        round.getQueueOf(entry - 1).await();
                    }
                    //ho la precedenza a SX, posso passare
                    //prendo la lock a DX
                    if (round.getLockOf(entry).tryLock()) {
                        try {
                            insideRound = true;
                            myQuad = entry;
                            round.entry(myQuad);
                            //sveglio chi stava attendendo la precedenza (quelli sulla DX)
                            round.getQueueOf(entry).signal();
                            debug(Thread.currentThread() + B + myQuad);
                        } finally {
                            round.getLockOf(entry).unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    round.getLockOf(entry - 1).unlock();
                }
            } else {
                while (myQuad != exit) {
                    debug(Thread.currentThread() + G + " myQuad = " + myQuad + " exit = " + exit + round);
                    if(round.getLockOf(myQuad).tryLock()) {
                        int oldQuad = myQuad;
                        try{
                            if(round.getLockOf(myQuad + 1).tryLock()) {
                                try{
                                    debug(Thread.currentThread() + H + " myQuad = " + myQuad + " exit = " + exit + round);
                                    round.getQueueOf(myQuad).signalAll();
                                    myQuad = (myQuad + 1) % Round.NQUAD;
                                    round.moveTo(myQuad);
                                } finally {
                                    round.getLockOf(myQuad).unlock();
                                }
                            }
                        } finally {
                            round.getLockOf(oldQuad).unlock();
                        }
                    }
                }

                round.exit(myQuad);
                iAmAlive = false;
                debug(Thread.currentThread() + F);
            }
        }
    }

    public void debug(String s) {
        if(DEBUG)
            System.out.println(s);
    }
}
