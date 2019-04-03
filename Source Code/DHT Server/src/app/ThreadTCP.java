import java.util.Iterator;
import java.util.Set;

public class ThreadTCP extends Thread{

    String msg;
    ServerTCP server;

    public void run(){
        while (true) {
            server = new ServerTCP();
            msg = server.getMsg();
            msgRec(msg);
            server = null;
            System.gc();
        }
    }

    public void msgRec(String msg) {
        String[] arr = msg.split(";");
        ClientUDP udp;
        ClientTCP tcp;
        String temp;
        Set<String> keys;
        Iterator<String> it;
        switch (Integer.parseInt(arr[0])) {
            case 1:
                if(msg.contains(DHTServer.server.ip)) {
                    udp = new ClientUDP(msg);
                    break;
                }
                msg = msg +";"+ DHTServer.server.id + ";" + DHTServer.server.ip;
                tcp = new ClientTCP(DHTServer.server.ipSuc, msg);
                break;
            case 4:
                if(msg.contains(DHTServer.server.ip)) {
                    System.out.println(DHTServer.server.h.toString());
                    break;
                }
                if (DHTServer.server.h.containsValue(arr[1])) {
                    keys = DHTServer.server.h.keySet();
                    it = keys.iterator();
                    while(it.hasNext()) {
                        temp = it.next();
                        if (DHTServer.server.h.get(temp).equals(arr[1])) {
                            it.remove();
                        }
                    }
                }
                msg = msg + ";" + DHTServer.server.ip;
                tcp = new ClientTCP(DHTServer.server.ipSuc, msg);
                break;
            default:
                break;
        }
    }

}
