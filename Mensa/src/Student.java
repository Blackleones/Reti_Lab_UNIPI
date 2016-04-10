import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by Developer on 15/03/2016.
 */
public class Student implements Callable<Dish> {
    public static final String A = ": Ho preso un piatto";
    public static final String B = ": ho mangiato";

    private int id;
    private Dish dish;

    Student(int id, Dish dish) {
        this.id = id;
        this.dish = dish;
    }

    @Override
    public Dish call() throws Exception {
        Main.debug(id + A + dish);
        Random rand = new Random(System.currentTimeMillis());
        Thread.sleep(rand.nextInt(5000 - 1000) + 1000);
        dish.changeState();
        Main.debug(id + B + dish);
        return dish;
    }
}
