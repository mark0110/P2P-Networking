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
        switch (Integer.parseInt(msg.substring(0, 1))) {
            case 1:
                System.out.println("test");
                break;
            case 2:
                System.out.println("this is it");
                break;
            default:
                break;
        }
    }

}
