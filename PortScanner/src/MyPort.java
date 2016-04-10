/**
 * Created by Developer on 23/03/2016.
 */
public class MyPort {
    private boolean status;
    private int nport;
    private String ip;

    MyPort(String ip, int nport) {
        status = false;
        this.ip = ip;
        this.nport = nport;
    }

    public void isOpen(){
        status = true;
    }

    public boolean isStatus() {
        return status;
    }

    public int getNport() {
        return nport;
    }

    public String getIp() {
        return ip;
    }
}
