import java.util.Random;

/**
 * Created by Developer on 15/03/2016.
 */
public class Rinsers implements Runnable {
    public static final String A = ": devo risciacquare";
    public static final String B = ": ho risciacquato";

    private Dish dish;

    Rinsers(Dish dish) {
        this.dish = dish;
    }

    @Override
    public void run() {
        Main.debug(A + dish);
        Random rand = new Random(System.currentTimeMillis());
        try {
            Thread.sleep(rand.nextInt(5000 - 1000) + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dish.changeState();
        Main.debug(B + dish);
    }
}
