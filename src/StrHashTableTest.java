import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

/**
 * Unit tests for the StrHashTable class.
 *
 * This test suite verifies the correct behavior of all public methods,
 * including insertion, deletion, contains, and handling of null arguments
 * It also ensures that boolean-returning methods behave as expected
 */
class StrHashTableTest {

    //******************************** SIGNAL TESTS ********************************
    //********************************    insert    ********************************

    /**
     * Check single insert()
     * This test is to make sure the insertion of one entry works correctly
     * It then checks to make sure it's contained in the table
     */
    @Test
    @DisplayName("insert() single insert test")
    void insert() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        assertTrue(strHashTable.contains("test"),"insert() - test should contain value return true");

    }

    /**
     * Check Double insert()
     * This test is to make sure the insertion of a double works correctly
     * The test will add the same key twice then check to make sure the count is 1
     */
    @Test
    @DisplayName("insert() insert the same thing twice")
    void insertDouble() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        strHashTable.insert("test","this is a test");
        assertEquals(1, strHashTable.count(),"insert() double - should be 1");

    }

    /**
     * Check NULL insert()
     * This test is to make sure if insert is invoked with a null, the table will ignore it gracefully
     * It will check to make sure the table remains empty
     */
    @Test
    @DisplayName("insert() null test")
    void insertNull() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert(null,null);
        assertTrue(strHashTable.isEmpty(),"insert() null isEmpty() should return True");

    }

    //********************************    delete    ********************************

    /**
     * Check delete() removes one element
     * This test is to make sure deleting a signal inserted element is successful
     * The test will insert a value then delete it and check if it remains
     */
    @Test
    @DisplayName("delete() single delete test")
    void delete() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        strHashTable.delete("test");
        assertFalse(strHashTable.contains("test"),"After delete() test should return false");

    }

    /**
     * Check delete() removes not in list
     * This test is to make sure the delete function doesn't remove a value not contained in the table
     * It will insert a value then delete a value known not to be in the table
     * Then it will check to see if the original value remains
     */
    @Test
    @DisplayName("delete() not in list")
    void deleteNotInList() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        strHashTable.delete("more");
        assertTrue(strHashTable.contains("test"),"delete() not in list - test should return true");

    }

    /**
     * Check delete() with NULL input
     * This test will call the delete function with a null then check the original value remains
     * It is to make sure the delete function handles null inserts gracefully
     */
    @Test
    @DisplayName("delete() null delete test")
    void deleteNull() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        strHashTable.delete(null);
        assertTrue(strHashTable.contains("test"),"After delete(NULL) test should return true");

    }

    //********************************   getString  ********************************

    /**
     * Check getString() returns the right string
     * This test will insert a value in to the table then call getString and insure the value is correct
     */
    @Test
    @DisplayName("getString() one value test")
    void getString() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        assertEquals("this is a test", strHashTable.getString("test"), "getString() should return 'this is a test'");

    }

    /**
     * Check getString() with NULL input
     * This test will call the getString with a null and check it handles it gracefully
     * It will insert a known test value then call getString with a null
     */
    @Test
    @DisplayName("getString() null test ")
    void getStringNull() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        assertNull(strHashTable.getString(null), "getString() should return null when null is input");

    }

    /**
     * Check getString() with NULL input
     * This test will call the getString with a key known to not be in the table and check it handles it gracefully
     */
    @Test
    @DisplayName("getString() not in list ")
    void getStringNotInList() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        assertNull(strHashTable.getString("more"), "getString() should return null when null is input");

    }

    //********************************    isEmpty   ********************************

    /**
     * Check isEmpty returns False with table is populated
     * This test will insert a value in to the table then check is empty returns false
     */
    @Test
    @DisplayName("isEmpty() false")
    void isEmptyFalse() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        assertFalse(strHashTable.isEmpty(),"isEmpty after should return False");

    }

    /**
     * Check isEmpty returns True when empty
     * This test is to make sure isEmpty will return false when the table is empty
     */
    @Test
    @DisplayName("isEmpty() true")
    void isEmptyTrue() {

        StrHashTable strHashTable = new StrHashTable();
        assertTrue(strHashTable.isEmpty(),"isEmpty after should return True");

    }

    //********************************     count    ********************************

    /**
     * Check count() is correct
     * This test is to make sure the count method works correctly
     * It will insert 3 values then make sure the count returns 3
     */
    @Test
    @DisplayName("Count() test simple")
    void count() {
        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        strHashTable.insert("more","this is another test");
        strHashTable.insert("again","this is again a test");
        assertEquals(3, strHashTable.count(),"count() should be 3");

    }

    /**
     * Check count() is correct
     * This test is to make sure the count method works correctly
     * It will create an empty table and check to make sure count returns 0
     */
    @Test
    @DisplayName("Count() none")
    void countNone() {

        StrHashTableCollisions strHashTable = new StrHashTableCollisions();
        assertEquals(0, strHashTable.count(),"count() should be 0");

    }

    //********************************    reHash    ********************************

    /**
     * Tests if the table properly doubles in size when it reaches its limit
     * This test will insert values and make sure the count extends past the default value of 16
     * Since this table doesn't handle collisions, the test checks for size increase
     * Note: The test could still fail if randomly generated strings hash to the same key
     */
    @Test
    @DisplayName("reHash() extension test")
    void reHash() {
        StrHashTable strHashTable = new StrHashTable();
        for (int i = 0; i < 100; i++) {
            strHashTable.insert(getRandomString(5),getRandomString(10));
        }
        assertTrue(strHashTable.count() > 16,"Count should be grater then 16");
    }

    //********************************     dump     ********************************

    /**
     * Check that bump() is outputting correctly
     * This test will insert some values and check to make sure the output contains the first correct line
     */
    @Test
    @DisplayName("dump() output test")
    void dump() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test", "this is a test");

        // Change output to Byte array capture and collect print stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        strHashTable.dump();

        System.setOut(originalOut);

        String output = outContent.toString();

        assertTrue(output.contains("1: test, this is a test"), "dump() - Missing entry for 'test'");

    }

    /**
     * Check that bump() is outputting correctly
     * This test will make sure the dump function outputs nothing when the table is empty
     */
    @Test
    @DisplayName("dump() empty output test")
    void dumpEmpty() {

        StrHashTable strHashTable = new StrHashTable();

        // Change output to Byte array capture and collect print stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        strHashTable.dump();

        System.setOut(originalOut);

        String output = outContent.toString();

        assertEquals("", output, "dump() empty - Expected empty string output");

    }

    //********************************   contains   ********************************

    /**
     * Check if contains() returns true
     * This test will insert a known value and check to make sure contains returns true
     */
    @Test
    @DisplayName("contains() true")
    void containsTrue() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        assertTrue(strHashTable.contains("test"),"contains() should return true");
    }

    /**
     * Check if contains() returns false
     * This test will insert a known value and then check for a value known to not be in the table
     */
    @Test
    @DisplayName("contains() false")
    void containsFalse() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        assertFalse(strHashTable.contains("more"),"contains() should return false");
    }

    /**
     * Check if contains() NULL returns false
     * This test is to make sure calling contains with a null value returns false and is handled gracefully
     */
    @Test
    @DisplayName("contains() null")
    void containsNull() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        assertFalse(strHashTable.contains(null), "contains() should return false for null key");

    }

    //******************************** Mutable TESTS ********************************

    /**
     * Check that contains works for more than one in the table
     */
    @Test
    @DisplayName("contains() mutable")
    void containsMultiple() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        strHashTable.insert("more","this is another test");
        strHashTable.insert("again","this is again a test");
        assertTrue(strHashTable.contains("test"),"contains() multiple - should return true test 1");
        assertTrue(strHashTable.contains("more"),"contains() multiple - should return true test 2");
        assertTrue(strHashTable.contains("again"),"contains() multiple - should return true test 3");

    }

    /**
     * Check that is empty works with ads and deletes
     */
    @Test
    @DisplayName("isEmpty() before/after insert/delete")
    void isEmptyMultiple() {

        StrHashTable strHashTable = new StrHashTable();
        assertTrue(strHashTable.isEmpty(),"isEmpty() mutable - before insert should return True");
        strHashTable.insert("test","this is a test");
        strHashTable.insert("more","this is another test");
        strHashTable.insert("again","this is again a test");
        assertFalse(strHashTable.isEmpty(),"isEmpty() mutable - after insert should return False");
        strHashTable.delete("test");
        strHashTable.delete("more");
        strHashTable.delete("again");
        assertTrue(strHashTable.isEmpty(),"isEmpty() mutable - after deletion should return True");

    }

    /**
     * Check to make sure count stays consistent be
     */
    @Test
    @DisplayName("count() before/after insert/delete")
    void countMutable() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        strHashTable.insert("more","this is another test");
        strHashTable.insert("again","this is again a test");
        assertEquals(3, strHashTable.count(),"count() mutable - after add 3 should be 3");
        strHashTable.delete("test");
        assertEquals(2, strHashTable.count(),"count() mutable - after delete 1 should be 2");
        strHashTable.delete("more");
        assertEquals(1, strHashTable.count(),"count() mutable - after delete 2 should be 1");
        strHashTable.delete("again");
        assertEquals(0, strHashTable.count(),"count() mutable - after delete 3 should be 0");

    }


    /**
     * Check getString() returns the right string when the table has more than one
     */
    @Test
    @DisplayName("getString() mutable")
    void getStringMutable() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test","this is a test");
        strHashTable.insert("more","this is another test");
        strHashTable.insert("again","this is again a test");
        assertEquals("this is a test", strHashTable.getString("test"), "getString() mutable - should return 'this is a test'");
        assertEquals("this is another test", strHashTable.getString("more"), "getString() mutable - should return 'this is another test'");
        assertEquals("this is again a test", strHashTable.getString("again"), "getString() mutable - should return 'this is again a test'");
    }

    /**
     * Check that bump() is outputting correctly
     */
    @Test
    @DisplayName("dump() mutable")
    void dumpMutable() {

        StrHashTable strHashTable = new StrHashTable();
        strHashTable.insert("test", "this is a test");
        strHashTable.insert("more", "this is another test");
        strHashTable.insert("again", "this is again a test");

        // Change output to Byte array capture and collect print stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        strHashTable.dump();

        System.setOut(originalOut);

        String output = outContent.toString();

        assertTrue(output.contains("test, this is a test"), "dump() mutable - Missing entry for '1: test, this is a test'");
        assertTrue(output.contains("more, this is another test"), "dump() mutable - Missing entry for '2: more, this is another test'");
        assertTrue(output.contains("again, this is again a test"), "dump() mutable - Missing entry for '3: again, this is again a test'");

    }

    //******************************** Utils ********************************

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