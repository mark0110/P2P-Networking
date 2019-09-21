import java.util.Hashtable;

//Object for each server to keep track of the parameters
public class ServerInstance {

    int id;
    String ipSuc;
    String ip;
    Hashtable<String, String> h;

    ServerInstance(int id, String ip, String ourIP){
        this.id = id;
        this.ipSuc = ip;
        this.ip = ourIP;
        this.h = new Hashtable<>();
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
