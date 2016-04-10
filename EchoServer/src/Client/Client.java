package Client;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Developer on 10/04/2016.
 */
public class Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    Client() {
        socket = new Socket();

        try {
            socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 1500));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            socket.setSoTimeout(100000);
            socket.setTcpNoDelay(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String m) {
        String reply = "";

        try {
            writer.write(m + "\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reply = reader.readLine();
            System.out.println("from server: " + reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client client = new Client();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String message = "";

        try {
            while(!(message = in.readLine()).equals("exit"))
                client.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.close();
    }
}
