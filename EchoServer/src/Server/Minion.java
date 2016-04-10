package Server;

import java.io.*;
import java.net.Socket;

/**
 * Created by Developer on 10/04/2016.
 */
public class Minion implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Minion(Socket client) {
        socket = client;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            socket.setSoTimeout(100000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String m = "";
        System.out.println(Thread.currentThread() + "server: new client");

        try {
            while((m = reader.readLine()) != null){
                System.out.println("from client: " + m);
                writer.write("received: " + m + "\r\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println(Thread.currentThread() + ":  close");
                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
