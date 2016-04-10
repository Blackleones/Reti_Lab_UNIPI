import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * Created by Developer on 15/03/2016.
 */
public class Main {
    public static final boolean DEBUG = true;

    public static void debug(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        ExecutorService studentService = Executors.newFixedThreadPool(100);
        ExecutorService dishWasherService = Executors.newFixedThreadPool(3);
        ExecutorService rinsersService = Executors.newFixedThreadPool(4);
        BlockingQueue<Future<Dish>> queue = new LinkedBlockingDeque<Future<Dish>>();

        for(int i = 0; i < 100; i++) {
           queue.add(studentService.submit(new Student(i, new Dish())));
        }

        for(Future<Dish> futDish : queue){
            queue.remove(futDish);
            try {
                Dish d = futDish.get();

                if(d.isDirty())
                    queue.add(dishWasherService.submit(new DishWhasher(d)));
                else
                    rinsersService.submit(new Rinsers(d));

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

        studentService.shutdown();
        dishWasherService.shutdown();
        rinsersService.shutdown();
    }

}
