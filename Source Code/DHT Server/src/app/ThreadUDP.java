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
        ClientUDP udp;
        ClientTCP tcp;
        switch (Integer.parseInt(msg.substring(0, 1))) {
            case 1:
                if(msg.contains(Main.server.ip)) {
                    udp = new ClientUDP(msg);
                    break;
                }
                msg = msg +";"+Main.server.id + ";" + Main.server.ip;
                tcp = new ClientTCP(Main.server.ipSuc, msg);
                break;
            case 2:
                break;
            default:
                break;
        }
    }
}
