import java.util.Iterator;
import java.util.Set;

public class ThreadUDP extends Thread {

    String msg;
    ServerUDP server;

    public void run() {

        while (true) {
            server = new ServerUDP();
            server.start();
            msg = server.getMsg();
            System.out.println(msg);
            msgRec(msg);
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
            case 2:
                DHTServer.server.h.put(arr[2], arr[3]);
                System.out.println(DHTServer.server.h.toString());
                //udp = new ClientUDP("2;"+arr[1]);
                break;
            case 3:
                if (!DHTServer.server.h.containsKey(arr[2])) {
                    System.out.println(arr[2]);
                    udp = new ClientUDP(msg + ";" + "404 Content Not Found");
                    break;
                }
                udp = new ClientUDP(msg + ";" + DHTServer.server.h.get(arr[2]));
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
