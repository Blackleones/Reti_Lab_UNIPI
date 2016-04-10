package Server;

import java.io.*;
import java.net.Socket;

/**
 * Created by Developer on 10/04/2016.
 */
public class Minion implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private BufferedReader fileReader;
    private BufferedWriter writer;
    private static final String FILEFOLDER = "./fileServer/";

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
        String filename = "";
        String line = "";

        System.out.println(Thread.currentThread() + ": new client");

        try {
                filename = reader.readLine();
                System.out.println("file requested: " + filename);

                if (!new File(FILEFOLDER + filename).exists()) {
                    System.out.println(Thread.currentThread() + "FILE \"" + filename + "\" NOT FOUND");
                    writer.write("ERROR-001: FILE NOT FOUND\r\n");
                    writer.flush();
                    close();
                    return;
                }

                writer.write("ok\r\n");
                writer.flush();
                fileReader = new BufferedReader(new FileReader(FILEFOLDER + filename));

                while ((line = fileReader.readLine()) != null) {
                    writer.write(line + "\r\n");
                    writer.flush();
                }

            System.out.println(Thread.currentThread() + "transfer ended");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void close() {
        try{
            System.out.println(Thread.currentThread() + " close");
            if(reader != null)reader.close();
            if(writer != null)writer.close();
            if(fileReader != null)fileReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
