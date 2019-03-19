package app;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
    }
    public int hashValue(String name){
        int hashValue = 0;
        for(int i = 0; i < name.length(); i++){
            hashValue += (int) name.charAt(i);
        }
        hashValue = hashValue % 4;
        return hashValue;
    }
}