import java.util.Iterator;
import java.util.Set;

//This thread is responsible for the UDP connection and the actions needed when a packet is received
public class ThreadUDP extends Thread {

    //initializing of variables
    String msg;
    ServerUDP server;

    //overriding the run method of the Thread class to change the action of this Thread to what I need it to do
    public void run() {

        while (true) {
            server = new ServerUDP();
            server.start();
            msg = server.getMsg();
            System.out.println("UDP received: "+ msg);
            msgRec(msg);
        }
    }

    //this method is executed once a String has been received sung a UDP connection
    public void msgRec(String msg) {
        String[] arr = msg.split(";");
        ClientUDP udp;
        ClientTCP tcp;
        String temp;
        Set<String> keys;
        Iterator<String> it;
        switch (Integer.parseInt(arr[0])) {
            case 1:
                if(msg.contains(DHTServer.server.ip) && arr.length > 2) {
                    udp = new ClientUDP(msg);
                    System.out.println("Table after init: "+ DHTServer.server.h.toString());
                    break;
                }
                msg = msg +";"+ DHTServer.server.id + ";" + DHTServer.server.ip;
                tcp = new ClientTCP(DHTServer.server.ipSuc, msg);
                break;
            case 2:
                DHTServer.server.h.put(arr[2], arr[3]);
                System.out.println("Table after adding file: "+ DHTServer.server.h.toString());
                //udp = new ClientUDP("2;"+arr[1]);
                //System.out.println(DHTServer.server.h.toString());
                break;
            case 3:
                if (!DHTServer.server.h.containsKey(arr[2])) {
                    udp = new ClientUDP(msg + ";" + "404 Content Not Found");
                    break;
                }
                udp = new ClientUDP(msg + ";" + DHTServer.server.h.get(arr[2]));
                break;
            case 4:
                if(msg.contains(DHTServer.server.ip) && arr.length > 2) {
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
                System.out.println("Table after exit: " + DHTServer.server.h.toString());
                tcp = new ClientTCP(DHTServer.server.ipSuc, msg);
                break;
            default:
                break;
        }
    }
}
