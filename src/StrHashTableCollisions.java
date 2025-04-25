public class StrHashTableCollisions {

    // A list to keep all the nodes
    private StrHashTableCollisions.node[] table;
    private int size;
    private int numCollisions;
    private int numElements;
    private int startingSize = 16;
    private double capacity = 0.8;

    private boolean printCount = false;
    private boolean printHash = false;



    /**
     * The constructor for now hasn't passed any arguments
     * It will set up the first array of nodes using the starting size variable
     */
    public StrHashTableCollisions() {

        table = new StrHashTableCollisions.node[startingSize];
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

        // Check for missing input
        if (key != null && value != null) {

            // Check if the table has space (capacity exceeds 80%)
            if ((double) numElements / table.length >= capacity) {
                System.out.println("ERROR: Capacity exceeded");
                System.out.println("** Rehashing table... **");
                reHash();
            }

            int hash = hashFunction(key );
            node insert = new node(key,value);

            // Check again to make sure the key doesn’t exist after rehashing
            if (table[hash] != null) {
                insert.next = table[hash];
                table[hash] = insert;
            }else{
                table[hash] = insert;
            }

            numElements++;
            if (printCount) System.out.println(numElements + " elements inserted");

        } else {
            System.out.println("** ERROR: No key/value input **");
            System.out.println("****     Nothing added     ****");
        }


    }

    // BROKEN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    /**
     * This is to remove a key and value paring from the hash table
     * @param k - The kay associated with the value you want to remove
     */
    public void delete (String k){

        int hash = hashFunction(k  );
        node read = table[hash];
        k = k.toLowerCase().trim();


        if (read != null){

            node prev ;
            while (read != null) {
                String compare = read.getKey().toLowerCase().trim();
                if (compare.equals(k)) {

                    System.out.println("WE FOUND IT NOW REMOVE IT ");

                }
                prev = read;
                read = read.next;
            }

        }

    }

    // WORKING
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

        // for the length of k/2 effectively
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
        if(printHash)System.out.println("hashFunction() Before mod Hash: " + hash);

        hash %= table.length;       // Modulo

        if(printHash)System.out.println("hashFunction(" + k + ") = " + hash);

        return hash;
    }

    // WORKING
    /**
     * This it to rehash the table and make it twice the size
     * Then it till take the old table rehash the move the content
     */
    private void reHash(){

        // Set up Working table, reset element counter, make new table
        numElements = 0;
        StrHashTableCollisions.node[] temp = table;
        table = new StrHashTableCollisions.node[table.length*2];

        // For every !null entry in the original table
        for(node n : temp){
            if(n != null){

                // While there is another linked node
                node read = n;
                while (read != null) {

                    // Get the hash for the current old node and make a new input node
                    int index = hashFunction(read.key );
                    node newTemp = new node(read.key, read.value);

                    // If no node exists at the index, just add it otherwise bump the list up one
                    if (table[index] == null) {
                        table[index] = newTemp;
                    } else {
                        newTemp.next = table[index];
                        table[index] = newTemp;
                    }

                    // increment element count
                    numElements++;
                    read = read.next;
                }
            }
        }


    }

    // WORKING
    /**
     * This will return if a key has a node associated with it
     * @param k - The key you're looking for
     * @return - True if there is a node with that key and a value otherwise false
     */
    public boolean contains(String k){

        System.out.println("contains");
        if (k == null){
            System.out.println("**    ERROR: No key input    **");
            System.out.println("contains()  Nothing to look for");
            return false;
        }

        int hash = hashFunction(k  );
        node read = table[hash];
        k = k.toLowerCase().trim();


        if (read != null){

            while (read != null) {
                String compare = read.getKey().toLowerCase().trim();
                if (compare.equals(k)) {
                    return true;
                }
                read = read.next;
            }

        }

        return false;
    }

    // WORKING
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

        int hash = hashFunction(k );
        node read = table[hash];
        k = k.toLowerCase().trim();

        if (read != null){

            while (read != null) {
                String compare = read.getKey().toLowerCase().trim();
                if (compare.equals(k)) {
                    return read.value;
                }
                read = read.next;
            }

        }

        System.out.println("ERROR: No such element -> " + k);
        return null;

    }

    // WORKING
    /**
     * This is to check if the table is empty
     * @return true if empty false is not
     */
    public boolean isEmpty(){

        // Re calculate num elements
        return size() == 0;

    }

    // WORKING
    /**
     * A super simple look to count the current occupied elements
     * @return The number of elements stored
     */
    public int size(){

        int count = 0;
        for(StrHashTableCollisions.node t : table){
            if (t != null) {
                count += t.count();
            }
        }
        return count;

    }

    // WORKING
    /**
     * Prints out all content of the hash table
     */
    public void dump(){

        // Start counter and work way through every item in the current table
        int counter = 1;
        int index = 0;
        for(StrHashTableCollisions.node t : table){

            // If t has a node in it
            if (t != null) {
                System.out.println(counter + " @ " + index + ": " + t.toString());
                counter++;

                // While next is not null, keep printing
                node temp = t;
                while (temp.next != null) {
                    System.out.println(counter + ": " + temp.next.toString());
                    temp = temp.next;
                    counter++;
                }
            }
            index++;
        }

        System.out.println("Size: " + table.length );
        System.out.println("Entries: " + size() + "Internal counter " + numElements);
        double capacity = (Math.round((((double) numElements / table.length) * 100) * 1_000) / 1_000.0);
        System.out.println("Capacity: " + capacity + "%" );

    }


    //********************************** NODE **********************************
    /**
     * This is the node class, and it used to keep the key and value pairs
     */
    private static class node {
        private String key; // The key associated with the value
        private String value; // The value associated with the kay in the table
        private node next;

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

        public int count(){
            return countRecursive(1);
        }

        private int countRecursive(int i){
            return (next != null)? next.countRecursive(i+1) : i;
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

}
