import java.util.Hashtable;

public class ServerInstance {

    int id;
    String ipSuc;
    String ip;
    Hashtable<Integer, String> h;

    ServerInstance(int id, String ip, String ourIP){
        this.id = id;
        this.ipSuc = ip;
        this.ip = ourIP;
    }

    public int getId() {
        return id;
    }

    public String getIpSuc() {
        return ipSuc;
    }

    public String getIp() {
        return ip;
    }
}
