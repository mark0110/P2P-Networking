import java.util.Iterator;
import java.util.Set;

//the DHT servers use 2 threads, 1 more the TCP connection and the other for the UDP connection
//this thread is responsible for accepting TCP String packets and performing the tasks based on what is received
public class ThreadTCP extends Thread {

    //initializing
    String msg;
    ServerTCP server;

    //overriding the run method of the Thread class to perform the tasks that my program needs to
    public void run() {
        while (true) {
            server = new ServerTCP();
            msg = server.getMsg();
            System.out.println("TCP received: " + msg);
            msgRec(msg);
            server = null;
            System.gc();
        }
    }

    //this method is executed once a message has been received
    //based on the first number in the String, the action needed to be performed, changes
    public void msgRec(String msg) {
        String[] arr = msg.split(";");
        ClientUDP udp;
        ClientTCP tcp;
        String temp;
        Set<String> keys;
        Iterator<String> it;
        switch (Integer.parseInt(arr[0])) {
            case 1:
                if (msg.contains(DHTServer.server.ip)) {
                    udp = new ClientUDP(msg);
                    System.out.println("Table after init: " + DHTServer.server.h.toString());
                    break;
                }
                msg = msg + ";" + DHTServer.server.id + ";" + DHTServer.server.ip;
                tcp = new ClientTCP(DHTServer.server.ipSuc, msg);
                break;
            case 4:
                if (msg.contains(DHTServer.server.ip)) {
                    System.out.println("Table after exit: " + DHTServer.server.h.toString());
                    break;
                }
                if (DHTServer.server.h.containsValue(arr[1])) {
                    keys = DHTServer.server.h.keySet();
                    it = keys.iterator();
                    while (it.hasNext()) {
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