import java.util.List;

/**
 * This is the standard hash table class with methods to handel one table
 * no Collision handling
 */
public class StrHashTable {

    // A list to keep all the nodes
    private node[] table;
    private int size;
    private int numCollisions;
    private int numElements;
    private int startingSize = 16;
    private double capacity = 0.8;
    private boolean reHashDump = false;



    /**
     * The constructor for now hasn't passed any arguments
     * It will set up the first array of nodes using the starting size variable
     */
    public StrHashTable() {

        table = new node[startingSize];
        size = 0;
        numCollisions = 0;
        numElements = 0;

    }

    /**
     * Insert is a method used to add a value to the hash table
     * There is no double handling
     * @param key - The key of your matched pair
     * @param value - The value to go with that key.
     */
    public void insert(String key, String value) {

        if (key != null && value != null) {

            // Check if the table has space (capacity exceeds 80%)
            if ((double) numElements / table.length >= capacity) {
                System.out.println("ERROR: Capacity exceeded");
                reHash();  // This should increase the table size and rehash all elements
            }

            // Check again to make sure the key doesn’t exist after rehashing
            if (contains(key)) {
                System.out.println("ERROR: Key already exists");
                System.out.println("**    Nothing added    **");
                return;
            }

            // Finally, insert the value
            table[hashFunction(key)] = new node(key, value);
            numElements++;
        } else {
            System.out.println("** ERROR: No key/value input **");
            System.out.println("****     Nothing added     ****");
        }

    }

    /**
     * This is to remove a key and value paring from the hash table
     * @param k - The kay associated with the value you want to remove
     */
    public void delete (String k){

        // Check k for input
        if (k != null && contains(k)) {
            table[hashFunction(k)] = null;
            System.out.println("--> " + k + " Was successfully deleted");
            numElements--;
            return;
        }

        // Error message if nothing done
        System.out.println((k == null) ? "** ERROR: Input String Null  **"
                                        : "**   ERROR: Key not found    **");
        System.out.println("* delete()   Nothing Removed    *");

    }

    /**
     * This is the hashing function it will take in your key and give back an index
     * @param k - The key you wish to hash
     * @return - an int with the index associated with that key
     */
    private int hashFunction(String k){

        // Set default value
        int hash = 0;

        if (k == null){
            System.out.println("**    ERROR: No key input    **");
            System.out.println("hashFunction()  Nothing to hash");
            return hash;
        }


        for (int i = 0; i < k.length(); i += 2) {
            char c1 = k.charAt(i);
            // if char 2 is blank or out of bounds, make it 0
            char c2 = (i + 1 < k.length()) ? k.charAt(i + 1) : 0;

            // Concatenate with shift a little faster than multiple?
            int pairValue = (c1 << 8) + c2;
            hash += pairValue;
        }

        // Shift up 16 and XOR
        int shifted = hash >> 16;   // Everything extra should fall off
        hash = hash ^ shifted;
        hash *= 31;                 // divide by prime
        //System.out.println("hashFunction() Before mod Hash: " + hash);
        hash %= table.length;       // Modulo

        //System.out.println("hashFunction()  Hash: " + hash);

        return hash;
    }

    /**
     * This it to rehash the table and make it twice the size
     * Then it till take the old table rehash the move the content
     */
    private void reHash(){

        numElements = 0;

        if(reHashDump){dump();}

        // Set up temp table and new table doubles the size
        node[] temp = table;
        table = new node[table.length*2];

        // Move all the content this will ignore doubles
        for(node n : temp){
            if(n != null){
                insert(n.key, n.value);
            }
        }

        if(reHashDump){dump();}

    }

    /**
     * This will return if a key has a node associated with it
     * @param k - The key you're looking for
     * @return - True if there is a node with that key and a value otherwise false
     */
    public boolean contains(String k){

        if (k == null){
            System.out.println("**    ERROR: No key input    **");
            System.out.println("contains()  Nothing to look for");
            return false;
        }

        return table[hashFunction(k)] != null && table[hashFunction(k)].getValue() != null;

    }

    /**
     * Check the table contains a key and string then return the value
     * @param k - the kay your looking for
     * @return - The string associated with the value if it contains it
     */
    public String getString(String k){

        if (k == null){
            System.out.println("**    ERROR: No key input    **");
            System.out.println("****    Nothing to get     ****");
            return null;
        }

        // Check if it's in the table and return value if true
        if (contains(k)){
            return table[hashFunction(k)].getValue();
        }

        System.out.println("ERROR: No such element -> " + k);
        return null;

    }

    /**
     * This is to check if the table is empty
     * @return true if empty false is not
     */
    public boolean isEmpty(){

        // Re calculate num elements
        return count() == 0;

    }

    /**
     * A super simple look to count the current occupied elements
     * @return The number of elements stored
     */
    public int count(){

        int count = 0;
        for(node t : table){
            if (t != null) count++;
        }
        numElements = count;
        return count;

    }

    /**
     * Prints out all content of the hash table
     */
    public void dump(){

        int counter = 0;
        for(node t : table){
            if (t != null) {
                counter++;
                System.out.println(counter + ": " + t.toString());
            }
        }

    }


    //********************************** NODE **********************************
    /**
     * This is the node class, and it used to keep the key and value pairs
     */
    private static class node {
        private String key; // The key associated with the value
        private String value; // The value associated with the kay in the table

        /**
         * This will make a new node with the input values
         * @param k - The key for this node
         * @param v - The value associated with it
         */
        public node(String k, String v){
            key = (k == null)  ? " " : k;
            value = (v==null) ? " " : v;
        }

        /**
         * @return The value stored in this node
         */
        public String getValue(){
            return value;
        }

        /**
         * @return The key for this node
         */
        public String getKey(){
            return key;
        }

        /**
         * To string override
         * @return - a string formatted for printing
         */
        @Override
        public String toString(){
            return key + ", " + value;
        }

    } // <--- Node END

} // <-- Main END
