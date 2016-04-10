import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Developer on 23/03/2016.
 */
public class Main {
    public static final String OPEN = Color.ANSI_GREEN + " OPEN" + Color.ANSI_RESET;
    public static final String CLOSE = Color.ANSI_RED + " CLOSE" + Color.ANSI_RESET;
    public static String ip = "127.0.0.1";
    public static final boolean ONLYOPEN = false;
    public static final boolean ONLYCLOSE = false;
    public static long startTime;

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();

        if(args.length != 0)
            ip = args[0];

        ExecutorService threads = Executors.newFixedThreadPool(7);
        List<Future<MyPort>> result = new ArrayList<Future<MyPort>>();

        for(int i = 0; i < 65535; i++)
            result.add(threads.submit(new Minion(new MyPort(ip, i))));

        threads.shutdown();

        for(Future<MyPort> myport : result) {
            try {
                MyPort port = myport.get();
                print(port);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("END.\nTIME:  " + (System.currentTimeMillis() - startTime));
    }

    private static void print(MyPort port) {
        String s = "IP: " + ip;

        if(ONLYOPEN && port.isStatus())
            System.out.println(s + " PORT: " + port.getNport() + OPEN);
        else if(ONLYCLOSE && !port.isStatus())
            System.out.println(s + " PORT: " + port.getNport() + CLOSE);
        else if(!ONLYOPEN && !ONLYCLOSE)
            System.out.println(s + " PORT: " + port.getNport() + (port.isStatus() ? OPEN : CLOSE));
    }
}
