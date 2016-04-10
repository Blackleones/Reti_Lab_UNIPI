/**
 * Created by Developer on 07/03/2016.
 */
public class Main {
    public static void main(String[] args) {
        Buffer b = new Buffer(7);

        for(int i = 0; i < 5; i++)
            new Thread(new Producer(b)).start();

        for(int i = 0; i < 4; i++)
            new Thread((new Consumer(b))).start();
    }
}
