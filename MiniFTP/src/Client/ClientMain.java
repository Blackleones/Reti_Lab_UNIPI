package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Developer on 10/04/2016.
 */
public class ClientMain {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String filename = null;

        System.out.println("filename: ");
        try {
            filename = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Client client = new Client(filename);
        client.downloadFile();
    }
}
