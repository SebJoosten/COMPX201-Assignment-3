import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A debugging class designed to stress test a hash table with collision handling
 * This simulates various insert/delete/get scenarios, including edge cases like nulls
 * This has been used to validate the table consistency of 30 known values after 2000000 random values where added and removed
 */
public class DebuggingCollisions {

    StrHashTableCollisions test = new StrHashTableCollisions();
    Random rand = new Random();
    boolean the2000000Test = false;

    // Used to store pairs of data (key-value)
    public record kvPAIR<K, V>(K key, V value) {}

    /**
     * A utility class to store key - value pairs for later use
     */
    public static class pairStorage<K, V> {

        // List of key - value pairs
        private List<Debugging.kvPAIR<K, V>> pairData = new ArrayList<>();

        /**
         * Adds a key - value pair to the list
         *
         * @param key - The key to be added
         * @param value - The value to be added
         */
        public void addPair(K key, V value) {
            Debugging.kvPAIR<K, V> newPair = new Debugging.kvPAIR<>(key, value);
            pairData.add(newPair);
        }

        /**
         * Retrieves the key-value pair at the specified index
         *
         * @param index - The index of the pair to retrieve
         * @return - The key - value pair at the specified index
         */
        public Debugging.kvPAIR<K,V> key(int index){
            return pairData.get(index);
        }
    }

    /**
     * Main method to execute debugging tasks and observe the behavior of the hash table
     * @param args Command-line arguments (not used here)
     */
    public static void main(String[] args) {
        DebuggingCollisions debug = new DebuggingCollisions();

        System.out.println("----- Random Add Test (1000 entries) -----");
        debug.randomAdds(1000);
        debug.dump();

        System.out.println("\n----- Add and Remove Test (1000 entries) -----");
        debug.addRemoveRandom(1000);

        System.out.println("\n----- Insert Random Length Strings Test (1000 entries) -----");
        debug.insertRandomLengeth(1000);

        System.out.println("\n----- Null Handling Test -----");
        debug.nullCheck();

        System.out.println("\n----- Random Remove Test (1000 attempts) -----");
        debug.randomRemove(1000);


        debug = new DebuggingCollisions();

        // Add a standard set of 30 and do tests again
        debug.test.insert("apple", "a fruit that keeps doctors away");
        debug.test.insert("banana", "a yellow fruit that monkeys love");
        debug.test.insert("cat", "a furry animal that purrs");
        debug.test.insert("dog", "man's best friend");
        debug.test.insert("elephant", "the largest land animal");
        debug.test.insert("fish", "an aquatic creature with gills");
        debug.test.insert("grape", "a small, round fruit often used to make wine");
        debug.test.insert("honey", "a sweet substance produced by bees");
        debug.test.insert("ice", "frozen water");
        debug.test.insert("juice", "a drink made from fruits or vegetables");

        debug.test.insert("kiwi", "a small, fuzzy fruit with green flesh");
        debug.test.insert("lemon", "a sour, yellow citrus fruit");
        debug.test.insert("monkey", "a playful primate");
        debug.test.insert("notebook", "a book for writing notes");
        debug.test.insert("orange", "a sweet, round citrus fruit");
        debug.test.insert("pencil", "a tool for writing or drawing");
        debug.test.insert("quilt", "a warm blanket made of fabric layers");
        debug.test.insert("rose", "a fragrant flower, often red or pink");
        debug.test.insert("sunflower", "a tall, yellow flower that follows the sun");
        debug.test.insert("applepie", "a classic dessert made with apples and pastry");

        debug.test.insert("bread", "a staple food made from flour, water, and yeast");
        debug.test.insert("carrot", "an orange vegetable that grows underground");
        debug.test.insert("dragonfly", "an insect with long wings that flies quickly");
        debug.test.insert("elephantseal", "a large sea mammal with a trunk-like nose");
        debug.test.insert("firefly", "a glowing insect found in warm climates");
        debug.test.insert("grapefruit", "a citrus fruit that is sour and slightly bitter");
        debug.test.insert("houseplant", "a plant that is kept indoors");
        debug.test.insert("icecream", "a sweet frozen dessert");
        debug.test.insert("jellyfish", "a marine animal with a gelatinous body");
        debug.test.insert("kangaroo", "a large marsupial native to Australia");

        System.out.println("\n----- Random Remove Test with standard set (1000 attempts) -----");
        debug.randomRemove(1000);

        System.out.println("\n----- Add and Remove Test with standard set (1000 entries) -----");
        debug.addRemoveRandom(1000);

        // Print and conform the standard set remain
        debug.dump();

        // This dose work but good luck
        //debug.addRemoveRandom(20000000);

        debug.dump();
        // Nice
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
     * Adds 'i' number of random pairs, shuffles them, and then removes them from the table
     * This method helps test it if the delete operation works correctly
     * @param i The number of elements to add and remove
     */
    public void addRemoveRandom(int i){

        pairStorage<String, String> pairStorage = new pairStorage<>();

        System.out.println("Adding random pair: " + i);

        for (int j = 0; j < i; j++) {
            String key = getRandomString(10);
            String value = getRandomString(100);
            pairStorage.addPair(key, value);

            test.insert(key, value);
        }

        // Shuffle the pairs and remove them from the hash table
        int count = 0;
        Collections.shuffle(pairStorage.pairData);
        for (int j = 0; j < pairStorage.pairData.size(); j++) {
            String key = pairStorage.pairData.get(j).key();
            if (!test.contains(key)) {
                count++;
                System.out.println("Failed: " + key);
            }
            System.out.println("Delete:  " + key);
            test.delete(key);
        }

        // Clear list and print results
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

        // Print counter - accuracy might vary is double string generated
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
     * Delete pass through
     * Used when this class is called externally as a pass-through
     */
    public void dump(){
        test.dump();
    }

    /**
     * Get value pass through
     * Used when this class is called externally as a pass-through
     */
    public String getValues(String key){
        return test.getString(key);
    }

    /**
     * Delete pass through
     * Used when this class is called externally as a pass-through
     */
    public void delete(String key){
        test.delete(key);
    }

    /**
     * Contains pass through
     * Used when this class is called externally as a pass-through
     */
    public boolean contains(String key){
        return test.contains(key);
    }

    /**
     * Sets the hash table instance for testing. This is useful for swapping different table implementations
     * Used when this class is called externally as a pass-through
     * @param table The StrHashTable instance to test
     */
    public void insertTable(StrHashTableCollisions table){
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
