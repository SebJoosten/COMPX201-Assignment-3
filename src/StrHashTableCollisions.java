public class StrHashTableCollisions {

    // A list to keep all the nodes
    private StrHashTableCollisions.node[] table;
    private int startingSize = 16;
    private double capacity = 0.8;
    public boolean printStats = false;

    // numElements tracks the total number of stored elements so insertions don't require recounting every time
    // It's also updated during count() as a precaution to ensure accuracy—optional, but helps prevent drift
    private int numElements;


    /**
     * Constructor initializes the hash table
     * Sets up the initial array of nodes based on the starting size
     */
    public StrHashTableCollisions() {
        table = new StrHashTableCollisions.node[startingSize];
        numElements = 0;
    }

    /**
     * Inserts a key-value pair into the hash table
     * If the table exceeds the specified capacity threshold (capacity), it triggers a resize
     * Handles collisions by chaining nodes at the corresponding index
     *
     * @param key   - The key to associate with the value
     * @param value - The value to associate with the key
     */
    public void insert(String key, String value) {



        if (key != null && value != null) {
            if (key.isEmpty() || value.isEmpty()) {
                System.err.println("ERROR: insert() Key or value is empty string - Nothing inserted");
                return;
            }

            // Check if the table has space (capacity exceeds "capacity")
            if ((double) numElements / table.length >= capacity) {
                System.err.println("LOG: insert() Capacity exceeded - Resizing from " + table.length + " to " + (table.length * 2));
                reHash();
            }

            // Check the key is not double
            if (contains(key)) {
                System.err.println("ERROR: insert() Key " + key + " already exists - Nothing inserted");
                return;
            }

            // Get hash and make node
            int hash = hashFunction(key);
            node insert = new node(key,value);

            // Handle collision by chaining nodes at the hashed index
            if (table[hash] != null) {
                insert.next = table[hash];
                table[hash] = insert;
            }else{
                table[hash] = insert;
            }

            numElements++;
            return;
        }

        // Log error message when key or value is null
        System.err.println((key == null || value == null)
                ? "ERROR: insert() received null key or value - Nothing inserted"
                : "ERROR: insert() Collision occurred. \"" + key + "\" -> \"" + value + "\" not inserted");

    }

    /**
     * Removes a key - value pair from the hash table
     * It searches for the key and removes it from the linked list at the corresponding index
     *
     * @param k - The key associated with the value to be removed
     */
    public void delete (String k){

        if (k == null) {
            System.err.println("ERROR: delete() received null key - Nothing to delete");
            return;
        }

        // Get hash > Grab node at index > set current and prev
        int hash = hashFunction(k);
        node current = table[hash];
        node prev = null;

        if (current == null) {
            System.err.println("ERROR: delete() no entry found at index " + hash + " for key \"" + k + "\".");
            return;
        }

            // Traverse linked list at this hash index
            while (current != null) {

                // compare the current node's key to the target key
                if (current.key.equals(k)) {
                    numElements--;

                    // Node to remove is head
                    if (prev == null) {
                        table[hash] = current.next;
                        System.err.println("LOG: delete() HEAD \"" + k + "\" was successfully deleted");

                    // Node to remove is middle or end
                    } else {
                        prev.next = current.next;
                            System.err.println("LOG: delete() BETWEEN/END "
                                                +   ((current.next == null) ? "next NULL " : "next TRUE ")
                                                +  k + " was successfully deleted");
                    }

                    return; // Leave once removed

                }

                // Move to the next node if the current one is not a match
                prev = current;
                current = current.next;

            }

        System.err.println("ERROR: delete() key \"" + k + "\" not found in chain at index " + hash + ".");

    }

    /**
     * Hashing function that takes a string key and returns a valid index in the table
     * Applies folding, bitwise pairing and mixing for better spread
     * Colin did mention the additional mixing, so I gave it a go
     *
     * @param k - The key to hash
     * @return An integer index for the key
     */
    private int hashFunction(String k){

        // Set default value
        int hash = 0;

        if (k == null) {
            System.err.println("ERROR: hashFunction() received null key - Returning 0");
            return hash;
        }

        // Bitwise folding - combine characters in pairs to reduce collisions
        for (int i = 0; i < k.length(); i += 2) {
            char c1 = k.charAt(i);

            // If the second char in the pair is missing, use 0
            char c2 = (i + 1 < k.length()) ? k.charAt(i + 1) : 0;

            // Fold the two chars into a single int using bit shift then combine
            int pairValue = (c1 << 8) + c2;
            hash += pairValue;
        }

        // Extra bit mixing
        // Shift right, XOR recombine, multiply by prime, then modulo to fit table
        int shifted = hash >> 16;
        hash = hash ^ shifted;
        hash *= 31;
        hash = Math.abs(hash) % table.length;

        return hash;

    }

    /**
     * Rehashes the table by doubling its size and reinserting the existing elements
     * The content from the old table is rehashed and moved to the new one
     */
    private void reHash(){

        // Create a temporary reference to the old table, reset counter, make new larger table
        StrHashTableCollisions.node[] temp = table;
        numElements = 0;
        table = new StrHashTableCollisions.node[table.length*2];

        // Iterate through each node in the old table
        for(node n : temp){
            if(n != null){

                // Traverse nodes at this index
                node read = n;
                while (read != null) {

                    // Rehash and make new node
                    int index = hashFunction(read.key );
                    node newTemp = new node(read.key, read.value);

                    // If no node exists at the index, insert it - otherwise, add it to the front of the list
                    if (table[index] == null) {
                        table[index] = newTemp;
                    } else {
                        newTemp.next = table[index];
                        table[index] = newTemp;
                    }

                    // Increment element count after insertion
                    numElements++;
                    read = read.next;
                }
            }
        }


    }

    /**
     * This checks if a key has a node associated with it in the hash table
     *
     * @param k - The key you're looking for
     * @return - True if there is a node with that key otherwise false
     */
    public boolean contains(String k){

        if (k == null) {
            System.err.println("ERROR: contains() received null key - Nothing to check");
            return false;
        }

        // Get hash
        int hash = hashFunction(k);
        node current = table[hash];

        // Traverse list at the hashed index to check for a match
        while (current != null) {
            if (current.key.equals(k)) {
                return true;
            }
            current = current.next;
        }

        // No match return false
        return false;

    }

    /**
     * Checks if a key exists in the table and returns the associated string value
     *
     * @param k - The key you're looking for
     * @return - The string associated with the key if found, otherwise null
     */
    public String getString(String k){

        if (k == null) {
            System.err.println("ERROR: getString() received null key. Nothing to return.");
            return null;
        }

        // Get hash before
        int hash = hashFunction(k );
        node current = table[hash];


        // Traverse list at the hashed index to check for the key
        while (current != null) {
            if (current.key.equals(k)) {
                return current.value;
            }
            current = current.next;
        }

        // No matching key is found, print error and return null
        System.err.println("ERROR: getString() No such element -> " + k);
        return null;

    }

    /**
     * Checks if the hash table is empty
     *
     * @return - true if the table is empty otherwise false
     */
    public boolean isEmpty(){

        // Calls count() to determine if any elements are stored in the table
        return count() == 0;

    }

    /**
     * Counts the number of elements currently stored in the hash table
     * Updates the internal count as part of the process
     *
     * @return - The number of elements stored in this hash table
     */
    public int count(){

        int count = 0;

        // Traverse the table and sum up counts from populated entries
        for(StrHashTableCollisions.node t : table){
            if (t != null) {

                // Delegates count to each node’s internal logic
                count += t.count();
            }
        }

        // Update internal count tracker — not strictly necessary, but ensures consistency
        numElements = count;
        return count;

    }

    /**
     * Prints out all content of the hash table
     */
    public void dump(){

        // Start counter and work way through every item in the current table
        int counter = 1;
        for(StrHashTableCollisions.node t : table){

            // While a node is not null, traverse down the list
            node temp = t;
            while (temp != null) {
                System.out.println(counter + ": " + temp.toString());
                temp = temp.next;
                counter++;
            }
        }

        if(printStats) {
            System.out.println("Size: " + table.length);
            System.out.println("Entries: " + count() + "Internal counter " + numElements);
            double capacity = (Math.round((((double) numElements / table.length) * 100) * 1_000) / 1_000.0);
            System.out.println("Capacity: " + capacity + "%");
        }

    }

    //********************************** NODE **********************************
    /**
     * Node class for storing key-value pairs in the hash table
     */
    private static class node {
        private String key;     // Key for the node
        private String value;   // Value associated with the key
        private node next;      // The next node in the list

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

        // Simplifies element counting by using a recursive helper
        public int count(){
            return countRecursive(1);
        }

        // Recursive count for linked nodes - kept private to reduce IDE clutter and limit external access
        private int countRecursive(int i){
            return (next != null)? next.countRecursive(i+1) : i;
        }

        /**
         * Overrides toString() to return a formatted string for printing
         * @return A string representation of the key-value pair
         */
        @Override
        public String toString(){
            return key + ", " + value;
        }

    }

}
