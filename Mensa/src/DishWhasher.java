import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by Developer on 15/03/2016.
 */
public class DishWhasher implements Callable<Dish> {
    public static final String A = ": sto per lavare il piatto";
    public static final String B = ": ho lavato il piatto";

    private Dish dish;

    DishWhasher(Dish dish) {
        this.dish = dish;
    }

    @Override
    public Dish call() throws Exception {
        Main.debug(A + dish);
        Random rand = new Random(System.currentTimeMillis());
        Thread.sleep(rand.nextInt(5000 - 1000) + 1000);
        dish.changeState();
        Main.debug(B + dish);
        return dish;
    }
}
