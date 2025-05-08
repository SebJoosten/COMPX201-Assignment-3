/**
 * This is the standard hash table class with methods to handel one table
 * no Collision handling
 */
public class StrHashTable {

    // A list to keep all the nodes
    private node[] table;
    private int startingSize = 16;
    private double capacity = 0.8;

    // numElements tracks the total number of stored elements so insertions don't require recounting every time
    // It's also updated during count() as a precaution to ensure accuracy—optional, but helps prevent drift
    private int numElements;


    /**
     * The constructor for now hasn't passed any arguments
     * It will set up the first array of nodes using the starting size variable
     */
    public StrHashTable() {

        table = new node[startingSize];
        numElements = 0;

    }

    /**
     * Insert is a method used to add a value to the hash table.
     * There is no double handling any collisions will be ignored
     * @param key - The key of your matched pair
     * @param value - The value to go with that key.
     */
    public void insert(String key, String value) {

        if (key != null && value != null) {

            // Check if the table has space (capacity exceeds 80%)
            if ((double) numElements / table.length >= capacity) {
                System.err.println("LOG: insert() Capacity exceeded. Resizing from " + table.length + " to " + (table.length * 2));
                reHash();
            }

            int index = hashFunction(key);

            // If the spot is empty, add it
            if (table[index] == null) {
                table[index] = new node(key, value);
                numElements++;
                System.err.println("LOG: insert() Inserted \"" + key + "\" -> \"" + value + "\" at index " + index);
                return;
            }

            System.err.println((key == null || value == null)
                    ? "ERROR: insert() received null key or value. Nothing inserted."
                    : "ERROR: insert() Collision occurred. \"" + key + "\" -> \"" + value + "\" not inserted.");
        }

    }

    /**
     * This is to remove a key and value paring from the hash table
     * @param k - The kay associated with the value you want to remove
     */
    public void delete (String k){

        // Check k is not null and check the table has an entry there
        if (k != null && table[hashFunction(k)] != null) {
            table[hashFunction(k)] = null;
            System.err.println("LOG: delete() \"" + k + "\" was successfully deleted.");
            numElements--;
            return;
        }

        // Print error if "key" is null or was not found in the table
        System.err.println((k == null)
                ? "ERROR: delete() received null key. Nothing deleted."
                : "ERROR: delete() \"" + k + "\" not found. Nothing deleted.");

    }

    /**
     * Hashes a given key and returns a table index
     * @param k - The key you wish to hash
     * @return - an int with the index associated with that key DEFAULT = 0
     */
    private int hashFunction(String k){

        // Set default value
        int hash = 0;

        if (k == null){
            System.err.println("ERROR: hashFunction() received null key. Returning default hash.");
            return hash;
        }

        // Combine character pairs into numeric values
        for (int i = 0; i < k.length(); i += 2) {
            char c1 = k.charAt(i);
            char c2 = (i + 1 < k.length()) ? k.charAt(i + 1) : 0;
            int pairValue = (c1 << 8) + c2;
            hash += pairValue;
        }

        // Mix bits and constrain to table size
        int shifted = hash >> 16;   // Shift right by 16 bits
        hash = hash ^ shifted;      // XOR with shifted value
        hash *= 31;                 // multiply by prime
        hash %= table.length;       // Modulo to fit table size

        return hash;
    }

    /**
     * Doubles the table size and rehashes all existing entries into the new table
     */
    private void reHash(){

        // Backup the current table and create a new, larger one. reset numElements
        numElements = 0;
        node[] temp = table;
        table = new node[table.length*2];

        // Rehash old values in to new table
        for(node n : temp){
            if(n != null){
                insert(n.key, n.value);
            }
        }

        System.err.println("LOG: reHash() - Table size doubled and contents rehashed.");

    }

    /**
     * Checks if the given key has an associated node with a value.
     * @param k - The key to check for.
     * @return True if the key exists and has a value, otherwise false.
     */
    public boolean contains(String k) {

        if (k == null) {
            System.err.println("ERROR: contains() received null key - Nothing to check");
            return false;
        }

        // Get index and return true if occupied otherwise false
        int index = hashFunction(k);
        return table[index] != null && table[index].value != null;
    }

    /**
     * Returns the value associated with the given key
     * @param k - The key to search for
     * @return - The value if found, otherwise null (don't like null returns)
     */
    public String getString(String k) {

        if (k == null) {
            System.err.println("ERROR: getString() received null key. Nothing to return.");
            return null;
        }

        if (contains(k)) {
            return table[hashFunction(k)].value;
        }

        System.err.println("ERROR: getString() No such element -> " + k);
        return null;
    }

    /**
     * Checks if the table is empty
     * @return true if empty, otherwise false
     */
    public boolean isEmpty() {
        return count() == 0;
    }

    /**
     * Counts the number of occupied elements in the table
     * @return the number of elements stored
     */
    public int count() {

        int count = 0;
        for (node t : table) {
            if (t != null) count++;
        }

        // Update num elements to be safe
        numElements = count;
        return count;

    }

    /**
     * Prints all content of the hash table
     */
    public void dump() {

        int counter = 0;
        for (node t : table) {
            if (t != null) {

                counter++;
                System.out.println(counter + ": " + t);

            }
        }

    }

    //********************************** NODE **********************************
    /**
     * Node class for storing key-value pairs in the hash table
     */
    private static class node {
        private String key;   // Key for the node
        private String value; // Value associated with the key

        /**
         * Creates a new node with the specified key and value
         * @param k - The key for this node
         * @param v - The value associated with it
         */
        public node(String k, String v){
            // Just a double check to avoid storing nulls
            if (k == null || v == null) {
                System.err.println("ERROR: node() Key and value cannot be null. DEFAULT applied");
            }
            key = (k == null)  ? " " : k;
            value = (v == null) ? " " : v;
        }

        /**
         * Overrides toString() to return a formatted string for printing.
         * @return A string representation of the key-value pair.
         */
        @Override
        public String toString(){
            return key + ", " + value;
        }

    } // <--- Node END

} // <-- Main END
