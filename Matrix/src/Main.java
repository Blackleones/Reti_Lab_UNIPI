import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Developer on 21/03/2016.
 */
public class Main {
    public static int size = 1000;
    public static void main(String[] args) {
        Integer m1[][] = new Integer[size][size];
        Integer m2[][] = new Integer[size][size];
        Integer m3[][] = new Integer[size][size]; //matrice risultato
        ExecutorService threads = Executors.newFixedThreadPool(20);
        List<Future<ResultHelper>> results = new ArrayList<Future<ResultHelper>>();

        Random r = new Random();

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                m1[i][j] = r.nextInt(10);
                m2[i][j] = r.nextInt(10);
            }
        }

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                results.add(threads.submit(new MatrixTask(m1, m2, i, j)));

        threads.shutdown();

        for(Future<ResultHelper> rsf : results) {
            try {
                ResultHelper rs = rsf.get();
                m3[rs.i][rs.j] = rs.r;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        print(m3);
    }

    private static void print(Integer[][] m) {
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++)
                System.out.print(m[i][j] + " ");
            System.out.println();
        }

        System.out.println("------------------");
    }
}
