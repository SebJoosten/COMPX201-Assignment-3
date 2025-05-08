import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A debugging class designed to stress test a hash table
 * This simulates various insert/delete/get scenarios, including edge cases like nulls
 */
public class Debugging {

    // Instance of the StrHashTable for testing
    StrHashTable test = new StrHashTable();
    Random rand = new Random();

    // Used to store pairs of data (key-value)
    record kvPAIR<K, V>(K key, V value) {}

    /**
     * A utility class to store key - value pairs for later use
     */
    public static class pairStorage<K, V> {

        // List of key - value pairs
        private List<kvPAIR<K, V>> pairData = new ArrayList<>();

        /**
         * Adds a key - value pair to the list
         *
         * @param key - The key to be added
         * @param value - The value to be added
         */
        public void addPair(K key, V value) {
            kvPAIR<K, V> newPair = new kvPAIR<>(key, value);
            pairData.add(newPair);
        }

        /**
         * Retrieves the key-value pair at the specified index
         *
         * @param index - The index of the pair to retrieve
         * @return - The key - value pair at the specified index
         */
        public kvPAIR<K,V> key(int index){
            return pairData.get(index);
        }
    }

    /**
     * Main method to execute debugging tasks and observe the behavior of the hash table
     * @param args Command-line arguments (not used here)
     */
    public static void main(String[] args) {
        Debugging debugging = new Debugging();

        // Test various methods of the StrHashTable
        debugging.randomAdds(100);
        debugging.addRemoveRandom(50);
        debugging.insertRandomLengeth(100);
        debugging.nullCheck();
        debugging.randomRemove(50);
        debugging.dump();
    }

    /**
     * This method adds 'i' number of random string key - value pairs to the hash table
     * @param i The number of random key - value pairs to add
     */
    public void randomAdds(int i){

        // Adding 'i' random pairs
        for (int j=0 ; j<i ; j++ ){
            test.insert(getRandomString(10) , getRandomString(100));
        }

    }

    /**
     * Adds 'i' number of random pairs, shuffles them, and then removes them from the table
     * This method helps test it if the delete operation works correctly
     * @param i The number of elements to add and remove
     */
    public void addRemoveRandom(int i){

        // A class to store the list of pairs
        pairStorage<String, String> pairStorage = new pairStorage<>();

        System.out.println("Adding random pair: " + i);

        // Add 'i' random pairs and save them in the list
        for (int j = 0; j < i; j++) {
            String key = getRandomString(10);
            String value = getRandomString(100);
            pairStorage.addPair(key, value);
            test.insert(getRandomString(10), getRandomString(100));
        }

        // Print to check before removal
        test.dump();

        // Shuffle the pairs and remove them from the hash table
        int count = 0;
        Collections.shuffle(pairStorage.pairData);
        for (int j = 0; j < pairStorage.pairData.size(); j++) {
            if (!test.contains(pairStorage.pairData.get(j).key)) {
                count++;
            }
            test.delete(pairStorage.pairData.get(j).key);
        }

        // Clear list - print results
        pairStorage.pairData.clear();
        test.dump();
        System.out.println("There are " + count + " failed");

    }

    /**
     * Inserts 'i' random key-value pairs with random lengths of keys and values
     * This method checks if the table handles variable lengths correctly
     * @param i The number of random pairs to insert
     */
    public void insertRandomLengeth(int i){

        System.out.println("Adding random pair with random lengths: " + i);
        String key,value;
        int count = 0;

        // Insert 'i' random key-value pairs of random lengths and insert them in to the table
        for (int j=0;j<i;j++){
            key = getRandomString(rand.nextInt(999));
            value = getRandomString(rand.nextInt(999));
            test.insert(key, value);
            if (!test.contains(key)) {
                count++;
            }
        }

        // Print counter Will be inaccurate as doubles are not accounted for in this list
        System.out.println("There are " + count + " failed potentially doubles");
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
     * Randomly removes keys from the table. The keys are randomly generated
     * This method helps test the behavior of the delete operation
     * @param i The number of attempts to delete random keys
     */
    public void randomRemove(int i){

        for (int j=0;j<i;j++){
            test.delete(getRandomString(rand.nextInt(999)));
        }

    }

    /**
     * Dumps the current state of the hash table to the console
     * This helps to visualize the contents of the table during testing
     * Used when this class is called externally as a pass-through
     */
    public void dump(){
        test.dump();
    }

    /**
     * Sets the hash table instance for testing. This is useful for swapping different table implementations
     * Used when this class is called externally as a pass-through
     * @param table The StrHashTable instance to test
     */
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
