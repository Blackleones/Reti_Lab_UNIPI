package Client;

import java.io.*;
import java.net.*;

/**
 * Created by Developer on 10/04/2016.
 */
public class Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private BufferedWriter inputFile;
    private String filename;
    private static final String FILEFOLDER = "./fileFolder/";

    Client(String filename){
        this.filename = filename;
        socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 1500));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            socket.setSoTimeout(100000);
            socket.setTcpNoDelay(true);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadFile() {
        String reply = "";
        System.out.println("send file request: " + filename);

        try {
            writer.write(filename + "\r\n");
            writer.flush();
            reply = reader.readLine();

            if(reply.contains("ERROR")) {
                System.err.println(reply);
                close();
                return;
            }

            inputFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILEFOLDER + filename)));
            System.out.println("downloading..");

            while((reply = reader.readLine()) != null) {
                inputFile.write(reply + "\r\n");
                inputFile.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(filename + " downloaded");
            close();
        }
    }

    private void close() {
        try {
            if(reader != null)reader.close();
            if(writer != null)writer.close();
            if(inputFile != null)inputFile.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
