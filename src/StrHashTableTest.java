import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

class StrHashTableTest {

    /**
     * insert1 Just inserts one element and makes sure it is in the table
     */
    @Test
    @DisplayName("Test to see if the insert works")
    void insert1() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        assertTrue(strHashTable.contains("test"),"Insert test should return true");

    }

    /**
     * insertNull inserts a null and makes sure the table remains empty
     */
    @Test
    @DisplayName("Test to check null insert")
    void insertNull() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert(null,null);
        assertTrue(strHashTable.isEmpty(),"insert null isEmpty should return True");

    }

    /**
     * containsBeforeInsert Checks the table doesn't contain an element before insert
     * and then checks to make sure it dose after insert
     * It also checks for a second key to check for false returns
     */
    @Test
    @DisplayName("Checks if the contains method returns correctly before and after add")
    void containsBeforeInsert() {

        StrHashTable strHashTable = new StrHashTable();
        assertFalse(strHashTable.contains("test"),"contains Before Insert test should return false");
        strHashTable.insert("test","this is a test");
        assertTrue(strHashTable.contains("test"),"contains After Insert test should return true");
        assertFalse(strHashTable.contains("something"),"contains \"something\" Insert test should return false");

    }

    /**
     * Simple test to check one element can be added then deleted
     */
    @Test
    @DisplayName("Test to check if delete works with 1 item")
    void delete() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        assertTrue(strHashTable.contains("test"),"Before delete test should return true");
        strHashTable.delete("test");
        assertFalse(strHashTable.contains("test"),"After delete test should return false");

    }

    /**
     * getString() test to make sure the inserted value returns the correct string assioated with it
     */
    @Test
    @DisplayName("Test to check if get string returns correctly with signal add")
    void getString() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        assertEquals(strHashTable.getString("test"),"this is a test", "getString should return 'this is a test'");

    }

    /**
     * isEmpty Test checks if the table is empty then adds something and checks again
     */
    @Test
    @DisplayName("Test to make sure is empty works on its own")
    void isEmpty() {

        StrHashTable strHashTable = new StrHashTable();
        assertTrue(strHashTable.isEmpty(),"isEmpty before should return True");
        strHashTable.insert("test","this is a test");
        assertFalse(strHashTable.isEmpty(),"isEmpty after should return False");

    }

    /**
     * isEmptyMultiple Checks to make sure after adding and removing more than one element isEmpty returns correctly
     */
    @Test
    @DisplayName("isEmptyMultiple Checks is empty with more then one add and remove")
    void isEmptyMultiple() {

        StrHashTable strHashTable = new StrHashTable();
        assertTrue(strHashTable.isEmpty(),"isEmptyMultiple before should return True ");
        strHashTable.insert("test","this is a test");
        strHashTable.insert("more","this is another test");
        strHashTable.insert("again","this is again a test");
        assertFalse(strHashTable.isEmpty(),"isEmptyMultiple after should return False after add 3");
        strHashTable.delete("test");
        strHashTable.delete("more");
        strHashTable.delete("again");
        assertTrue(strHashTable.isEmpty(),"isEmptyMultiple after deletion should return True");

    }

    /**
     * Simple test to make sure the size works correctly
     * Adds 3 elements and removes them one by one rechecking count
     */
    @Test
    @DisplayName("Count test")
    void count() {
        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        strHashTable.insert("more","this is another test");
        strHashTable.insert("again","this is again a test");
        assertEquals(strHashTable.count(),3,"Count should be 3");
        strHashTable.delete("test");
        assertEquals(strHashTable.count(),2,"Count should be 2");
        strHashTable.delete("more");
        assertEquals(strHashTable.count(),1,"Count should be 1");
        strHashTable.delete("again");
        assertEquals(strHashTable.count(),0,"Count should be 0");
    }

    /**
     * This is a test to make sure the table extends its size when hitting its limit,
     * Being that this table doesn't deal with doubles, we are only looking for it to have increased in size
     * This test could still potentially fail is random string generates values that hash to the same key
     */
    @Test
    @DisplayName("Test to make sure the table extends when hitting a limit")
    void reHash() {
        StrHashTable strHashTable = new StrHashTable();
        for (int i = 0; i < 100; i++) {
            strHashTable.insert(getRandomString(5),getRandomString(10));
        }
        assertTrue(strHashTable.count() > 16,"Count should be grater then 16");
    }

    /**
     * Very basic test to check dump and make sure it outputs correctly
     */
    @Test
    @DisplayName("Dump test Check output vs expected ")
    void dump() {
        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        strHashTable.insert("more","this is another test");
        strHashTable.insert("again","this is again a test");

        // Change output to Byte array capture and collect print stream
        // Any System.out.print now goes into outContent instead of the console
        // https://docs.oracle.com/javase/8/docs/api/java/io/ByteArrayOutputStream.html
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        strHashTable.dump();

        // Note to future self """" something """" you can input text "Java Text Blocks"
        // Based on a Manually written out txt doc
        String dumpResult = """
        1: test, this is a test
        2: more, this is another test
        3: again, this is again a test
        """.trim();

        // Set output back to console instead of the capture array
        System.setOut(originalOut);
        // Put output in to capture String for compare and replace line separators
        String output = outContent.toString().replaceAll("\\r\\n?", "\n").trim();

        assertEquals(dumpResult, output, "Dump Output docent match expected \n" + dumpResult );

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