import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class test {

    public static void main(String args[]) {
        //ClientUDP server = new ClientUDP("1;10.17.135.179;1;1");
        //String i = "1;2;3;4";
        //String[] n = i.split(";");
        //System.out.println(n[2]);
        Hashtable<String, String> h = new Hashtable<>();
        h.put("1", "a");
        h.put("2", "b");
        h.put("3", "c");
        h.put("4", "d");
        System.out.println(h.toString());
        Set<String> keys = h.keySet();
        Iterator<String> it = keys.iterator();
        String temp = "";

        for(int i = 0; i < h.size(); i++){

            //System.out.println(h.get(keys.iterator().next()).toString());
            if (h.get(temp).equals("b")) {
                h.remove(temp);
            }
            }
        System.out.println(h.toString());
        }

    }

