/**
 * Created by Developer on 07/03/2016.
 */
public class Main {
    private static final int nThread = 5;

    public static void main(String[] args) {
        Microphone mic = new Microphone(nThread);

        for(int i = 0; i < nThread; i++)
            new Thread(new Student(mic)).start();
    }
}
