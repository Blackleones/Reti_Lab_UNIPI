import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 11/03/2016.
 */
public class Main {
    public static int nCar = 10;

    public static void main(String[] args) {
        Round round = new Round();
        List threads = new ArrayList<Thread>();

        for(int i = 0; i < nCar; i++) {
            Thread t = new Thread(new Car(round));
            threads.add(t);
            t.start();
        }
    }
}
