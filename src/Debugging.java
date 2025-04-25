import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This is a debugging class used to abuse the hash table
 */
public class Debugging {

    StrHashTable test = new StrHashTable();
    Random rand = new Random();

    // Used to store pairs of data
    private record kvPAIR<K, V>(K key, V value) {}
    public static class pairStorage<K, V> {
        private List<kvPAIR<K, V>> pairData = new ArrayList<>(); // Using ArrayList instead of List for direct instantiation

        public void addPair(K key, V value) {
            kvPAIR<K, V> newPair = new kvPAIR<>(key, value);
            pairData.add(newPair);
        }

        public kvPAIR<K,V> key(int index){
            return pairData.get(index);
        }
    }

    /**
     * Constructor
     */
    public Debugging() {

    }

    /**
     * This will just add the number of random inputs you want
     * @param i The amount of string you want to add
     */
    public void randomAdds(int i){

        for (int j=0;j<i;j++){
            test.insert(getRandomString(10) , getRandomString(100));
        }

    }

    /**
     * A method to add a bunch of random values them remove them ALL
     * @param i - the number of elements to add and remove
     */
    public void addRemoveRandom(int i){

        pairStorage<String, String> pairStorage = new pairStorage<>();

        System.out.println("Adding random pair: " + i);

        for (int j = 0; j < i; j++) {
            String key = getRandomString(10);
            String value = getRandomString(100);
            pairStorage.addPair(key, value);

            test.insert(getRandomString(10), getRandomString(100));
        }

        test.dump();

        // Shuffle then remove from table
        int count = 0;
        Collections.shuffle(pairStorage.pairData);
        for (int j = 0; j < pairStorage.pairData.size(); j++) {
            if (!test.contains(pairStorage.pairData.get(j).key)) {
                count++;
            }
            test.delete(pairStorage.pairData.get(j).key);
        }
        pairStorage.pairData.clear();
        test.dump();

        System.out.println("There are " + count + " failed");

    }

    /**
     * inserts i number of key and value pairs with random string lengths from 1-999
     * @param i - The number of random strings to add
     */
    public void insertRandomLengeth(int i){

        System.out.println("Adding random pair with random lengths: " + i);
        String key,value;
        int count = 0;

        for (int j=0;j<i;j++){
            key = getRandomString(rand.nextInt(999));
            value = getRandomString(rand.nextInt(999));
            test.insert(key, value);
            if (!test.contains(key)) {
                count++;
            }
        }

        System.out.println("There are " + count + " failed");
    }

    /**
     * Inserts, checks and deletes null items
     */
    public void nullCheck(){
        test.insert(null, null);
        test.contains(null);
        test.delete(null);
    }

    /**
     * Randomly tries to remove random keys of length 1 - 999
     * @param i The number of attempts
     */
    public void randomRemove(int i){
        for (int j=0;j<i;j++){
            test.delete(getRandomString(rand.nextInt(999)));
        }
    }

    public void dump(){
        test.dump();
    }

    public void insertTable(StrHashTable table){
        test = table;
    }

    /**
     * Random string generator ripped from previous project
     * You can alter the "CHARACTERS" String to add other custom characters
     */
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String getRandomString(int length) {

        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));

        }

        return sb.toString();

    }




}
