package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Developer on 10/04/2016.
 */
public class Server {
    public static void main(String[] args) {
        BufferedReader reader = null;
        BufferedReader writer = null;

        System.out.println("started");

        try(ServerSocket serverSocket = new ServerSocket()){
            serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 1500));
            System.out.println("connected");

            while(true) {
                System.out.println("waiting for new clients..");
                Socket client = serverSocket.accept();
                new Thread(new Minion(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
