import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by Developer on 23/03/2016.
 */
public class Minion implements Callable<MyPort> {
    private MyPort port;
    private static final int timeout = 200;

    Minion(MyPort port) {
        this.port = port;
    }

    @Override
    public MyPort call() {
        Socket socket = new Socket();
        try {
            socket.connect(makeAddress(), timeout);
            port.isOpen();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return port;
    }

    private InetSocketAddress makeAddress() {
        return new InetSocketAddress(port.getIp(), port.getNport());
    }
}
