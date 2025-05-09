import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

/**
 * Unit tests for the StrHashTableCollisions class.
 *
 * These tests verify the baseline functionality of all public methods,
 * including insertion, deletion, contains, and null handling.
 * Boolean-returning methods are checked for correct behavior.
 *
 * Note: More aggressive stress testing is performed separately via
 * the DebuggingCollisions class, which has validated table consistency
 * through over 2,000,000 insertions and deletions.
 *
 */
class StrHashTableCollisionsTest {


    /**
     * General hash table set up used for each method
     * the occasional method has to reset it for testing, but it was easier to follow by a method group
     */
    private StrHashTableCollisions hashTableCollisions;
    @BeforeEach
    void setUp() {
        hashTableCollisions = new StrHashTableCollisions();
        hashTableCollisions.insert("test","this is a test");
    }

    //******************************** SIGNAL TESTS ********************************
    //********************************    insert    ********************************

    @org.junit.jupiter.api.Nested
    class InsertTests {

        /**
         * Check single insert()
         * This test is to make sure the insertion of one entry works correctly
         * It then checks to make sure it's contained in the table
         */
        @Test
        @DisplayName("insert() Single insert test")
        void insert() {

            assertTrue(hashTableCollisions.contains("test"), "insert() - test should contain value return true");

        }

        /**
         * Check Double insert()
         * This test is to make sure the insertion of a double works correctly
         * The test will add the same key twice then check to make sure the count is 1
         * A double is not the same as a collision
         * In the case of a double, the keys must also match not just the index
         */
        @Test
        @DisplayName("insert() Insert the same thing twice")
        void insertDouble() {

            hashTableCollisions.insert("test", "this is a test");
            assertEquals(1, hashTableCollisions.count(), "insert() double - should be 1");

        }

        /**
         * Check NULL insert()
         * This test is to make sure if insert is invoked with a null, the table will ignore it gracefully
         * It will check to make sure the table remains empty
         */
        @Test
        @DisplayName("insert() Null test")
        void insertNull() {

            hashTableCollisions = new StrHashTableCollisions();
            hashTableCollisions.insert(null, null);
            assertTrue(hashTableCollisions.isEmpty(), "insert() null isEmpty() should return True");

        }

        /**
         * Check missing key insert()
         * This test is to make sure if insert is invoked with an empty, the table will ignore it gracefully
         * It will check to make sure the table remains with 1 entry and Key == "" is not entered
         */
        @Test
        @DisplayName("insert() Missing key test")
        void insertMissingKey() {

            hashTableCollisions.insert("", "Missing key test");
            assertFalse(hashTableCollisions.contains(""), "insert() missing key contains nothing should return False");
            assertEquals(1, hashTableCollisions.count(), "insert() missing key should return 1 not 2");

        }

        /**
         * Check missing value insert()
         * This test is to make sure if insert is invoked with an empty value, the table will ignore it gracefully
         * It will check to make sure the table remains with 1 entry and value == "" is not entered
         */
        @Test
        @DisplayName("insert() Missing value test")
        void insertMissingValue() {

            hashTableCollisions.insert("testing", "");
            assertFalse(hashTableCollisions.contains(""), "insert() missing key contains nothing should return False");
            assertEquals(1, hashTableCollisions.count(), "insert() missing value should return 1 not 2");

        }

    }

    //********************************    delete    ********************************

    @org.junit.jupiter.api.Nested
    class DeleteTests {

        /**
         * Check delete() removes one element
         * This test is to make sure deleting a signal inserted element is successful
         * The test will insert a value then delete it and check if it remains
         */
        @Test
        @DisplayName("delete() single delete test")
        void delete() {

            hashTableCollisions.delete("test");
            assertFalse(hashTableCollisions.contains("test"), "After delete() test should return false");

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

            hashTableCollisions.delete("more");
            assertTrue(hashTableCollisions.contains("test"), "delete() not in list - test should return true");

        }

        /**
         * Check delete() with NULL input
         * This test will call the delete function with a null then check the original value remains
         * It is to make sure the delete function handles null inserts gracefully
         */
        @Test
        @DisplayName("delete() null delete test")
        void deleteNull() {

            hashTableCollisions.delete(null);
            assertTrue(hashTableCollisions.contains("test"), "After delete(NULL) test should return true");

        }

    }

    //********************************   getString  ********************************

    @org.junit.jupiter.api.Nested
    class GetStringTests {

        /**
         * Check getString() returns the right string
         * This test will insert a value in to the table then call getString and insure the value is correct
         */
        @Test
        @DisplayName("getString() one value test")
        void getString() {

            assertEquals("this is a test", hashTableCollisions.getString("test"), "getString() should return 'this is a test'");

        }

        /**
         * Check getString() with NULL input
         * This test will call the getString with a key known to not be in the table and check it handles it gracefully
         */
        @Test
        @DisplayName("getString() not in list ")
        void getStringNotInList() {

            assertNull(hashTableCollisions.getString("more"), "getString() should return null when null is input");

        }

        /**
         * Check getString() with NULL input
         * This test will call the getString with a null and check it handles it gracefully
         * It will insert a known test value then call getString with a null
         */
        @Test
        @DisplayName("getString() null test ")
        void getStringNull() {

            assertNull(hashTableCollisions.getString(null), "getString() should return null when null is input");

        }

    }

    //********************************    isEmpty   ********************************
    @org.junit.jupiter.api.Nested
    class InEmptyTests {

        /**
         * Check isEmpty returns False with table is populated
         * This test will insert a value in to the table then check is empty returns false
         */
        @Test
        @DisplayName("isEmpty() false")
        void isEmptyFalse() {

            assertFalse(hashTableCollisions.isEmpty(), "isEmpty after should return False");

        }

        /**
         * Check isEmpty returns True when empty
         * This test is to make sure isEmpty will return false when the table is empty
         */
        @Test
        @DisplayName("isEmpty() true")
        void isEmptyTrue() {

            hashTableCollisions = new StrHashTableCollisions();
            assertTrue(hashTableCollisions.isEmpty(), "isEmpty after should return True");

        }

    }

    //********************************     count    ********************************

    @org.junit.jupiter.api.Nested
    class CountTests {

        /**
         * Check count() is correct
         * This test is to make sure the count method works correctly
         * It will insert 3 values then make sure the count returns 3
         */
        @Test
        @DisplayName("Count() 3")
        void count() {

            hashTableCollisions.insert("more", "this is another test");
            hashTableCollisions.insert("again", "this is again a test");
            assertEquals(3, hashTableCollisions.count(), "count() should be 3");

        }

        /**
         * Check count() is correct
         * This test is to make sure the count method works correctly
         * It will create an empty table and check to make sure count returns 0
         */
        @Test
        @DisplayName("Count() none")
        void countNone() {

            hashTableCollisions.delete("test");


        }

    }

    //********************************    reHash    ********************************

    /**
     * Tests if the table properly doubles in size when it reaches its limit
     * This test will insert values and make sure the count extends past the default value of 16
     * Note: The test could still fail if randomly generated strings hash to the same key
     */
    @Test
    @DisplayName("reHash() extension test")
    void reHash() {

        for (int i = 0; i < 100; i++) {
            hashTableCollisions.insert(getRandomString(5),getRandomString(10));
        }
        assertTrue(hashTableCollisions.count() > 16,"reHash() extension test - Count should be grater then 16");

    }

    //********************************     dump     ********************************

    @org.junit.jupiter.api.Nested
    class DumpCaptureTests {

        ByteArrayOutputStream outContent;
        PrintStream originalOut;

        // Set up the capture of the output
        @BeforeEach
        void setUp() {

            // Capture System.out
            outContent = new ByteArrayOutputStream();
            originalOut = System.out;
            System.setOut(new PrintStream(outContent));
        }

        /**
         * Check that bump() is outputting correctly
         * This test will insert some values and check to make sure the output contains the first correct line
         */
        @Test
        @DisplayName("dump() output test")
        void dump() {

            hashTableCollisions.dump();

            String output = outContent.toString();

            assertTrue(output.contains("1: test, this is a test"), "dump() - Missing entry for 'test'");

        }

        /**
         * Check that bump() is outputting correctly
         * This test will make sure the dump function outputs nothing when the table is empty
         * This test will fail if printStats enabled - DEFAULT disabled
         */
        @Test
        @DisplayName("dump() empty output test")
        void dumpEmpty() {

            hashTableCollisions.delete("test");
            hashTableCollisions.dump();

            String output = outContent.toString();

            assertEquals("", output, "dump() empty - Expected empty string output");

        }

        /**
         * Check that bump() is outputting correctly
         */
        @Test
        @DisplayName("dump() mutable")
        void dumpMutable() {

            hashTableCollisions.insert("more", "this is another test");
            hashTableCollisions.insert("again", "this is again a test");

            hashTableCollisions.dump();

            String output = outContent.toString();

            hashTableCollisions.dump();

            assertTrue(output.contains("1: test, this is a test"), "dump() mutable - Missing entry for '1: test, this is a test'");
            assertTrue(output.contains("2: more, this is another test"), "dump() mutable - Missing entry for '2: more, this is another test'");
            assertTrue(output.contains("3: again, this is again a test"), "dump() mutable - Missing entry for '3: again, this is again a test'");

        }

        // Reset output capture
        @AfterEach
        void tearDown() {
            System.setOut(originalOut);
        }

    }

    //********************************   contains   ********************************

    @org.junit.jupiter.api.Nested
    class ContainsTests {

        /**
         * Check if contains() returns true
         * This test will insert a known value and check to make sure contains returns true
         */
        @Test
        @DisplayName("contains() true")
        void containsTrue() {

            assertTrue(hashTableCollisions.contains("test"), "contains() should return true");

        }

        /**
         * Check if contains() returns false
         * This test will insert a known value and then check for a value known to not be in the table
         */
        @Test
        @DisplayName("contains() false")
        void containsFalse() {

            assertFalse(hashTableCollisions.contains("more"), "contains() should return false");

        }

        /**
         * Check if contains() NULL returns false
         * This test is to make sure calling contains with a null value returns false and is handled gracefully
         */
        @Test
        @DisplayName("contains() null")
        void containsNull() {

            assertFalse(hashTableCollisions.contains(null), "contains() should return false for null key");

        }

    }

    //******************************** Mutable TESTS ********************************

    @org.junit.jupiter.api.Nested
    class MutableTests {

        // This just adds some more values in on top of the normal test value before Mutable tests
        @BeforeEach
        void setUp() {
            hashTableCollisions.insert("more", "this is another test");
            hashTableCollisions.insert("again", "this is again a test");
        }

        /**
         * Check that contains works for more than one in the table
         */
        @Test
        @DisplayName("contains() mutable")
        void containsMultiple() {

            assertTrue(hashTableCollisions.contains("test"),"contains() multiple - should return true test 1");
            assertTrue(hashTableCollisions.contains("more"),"contains() multiple - should return true test 2");
            assertTrue(hashTableCollisions.contains("again"),"contains() multiple - should return true test 3");

        }

        /**
         * Check to make sure count stays consistent be
         */
        @Test
        @DisplayName("count() before/after insert/delete")
        void countMutable() {

            assertEquals(3, hashTableCollisions.count(), "count() mutable - after add 3 should be 3");
            hashTableCollisions.delete("test");
            assertEquals(2, hashTableCollisions.count(), "count() mutable - after delete 1 should be 2");
            hashTableCollisions.delete("more");
            assertEquals(1, hashTableCollisions.count(), "count() mutable - after delete 2 should be 1");
            hashTableCollisions.delete("again");
            assertEquals(0, hashTableCollisions.count(), "count() mutable - after delete 3 should be 0");

        }

        /**
         * Check getString() returns the right string when the table has more than one
         */
        @Test
        @DisplayName("getString() mutable")
        void getStringMutable() {

            assertEquals("this is a test", hashTableCollisions.getString("test"), "getString() mutable - should return 'this is a test'");
            assertEquals("this is another test", hashTableCollisions.getString("more"), "getString() mutable - should return 'this is another test'");
            assertEquals("this is again a test", hashTableCollisions.getString("again"), "getString() mutable - should return 'this is again a test'");
        }

        /**
         * This test is to double-check the delete, and is empty functions work together correctly
         * The idea is to catch compounding errors from more than one operation
         */
        @Test
        @DisplayName("isEmpty() before/after delete")
        void isEmptyMultipleDelete() {

            assertFalse(hashTableCollisions.isEmpty(),"isEmpty() mutable - after insert should return False");
            hashTableCollisions.delete("test");
            hashTableCollisions.delete("more");
            hashTableCollisions.delete("again");
            assertTrue(hashTableCollisions.isEmpty(),"isEmpty() mutable - after deletion should return True");

        }

        /**
         * This test is to double-check the insert and delete functions work together correctly
         *  The idea is to catch compounding errors from more than one operation
         */
        @Test
        @DisplayName("isEmpty() before/after insert")
        void isEmptyMultipleInsert() {

            hashTableCollisions.delete("test");
            hashTableCollisions.delete("more");
            hashTableCollisions.delete("again");
            assertTrue(hashTableCollisions.isEmpty(),"isEmpty() mutable - after deletion should return True");
            hashTableCollisions.insert("test","this is a test");
            hashTableCollisions.insert("more", "this is another test");
            hashTableCollisions.insert("again", "this is again a test");
            assertFalse(hashTableCollisions.isEmpty(),"isEmpty() mutable - after insert should return False");

        }

        /**
         * This is to test special characters in keys
         * First it will insert the content of the array below then validate
         * It will then run a delete test to make sure they are removed correctly
         */
        @org.junit.jupiter.api.Nested
        class specialCharactersTests {
            String[] specialKeys;

            // Set up and insert the array of special strings on top of the initial 3
            @BeforeEach
            void setUp() {
                specialKeys = new String[]{
                        "key with spaces", // spaces
                        "key-with-hyphens", // dash
                        "key_with_underscores", // underscore
                        "key.with.dots", // dots
                        "key@with!special#chars$%^&*()",
                        "()[{}]<>",// brackets
                        "←↑→↓↔⇧",// arrows
                        "£€¥₩₹₽", // currency
                        "😀😂🔥👍",// emojis
                        "☃",            // the show man
                        "αβγδε", // Other languages and special characters including a right-to-left language
                        "™©®✓",
                        "𝔘𝔫𝔦𝔠𝔬𝔡𝔢",
                        "零一二三",
                        "éçñø",
                        "こんにちは",
                        "שָׁלוֹם"

                };

                // Add all the special entries to the hash table
                for (int i = 0; i < specialKeys.length; i++) {
                    hashTableCollisions.insert(specialKeys[i], "value" + i);
                }

            }

            /**
             * Test to validate the insertion of all the special character strings
             * It then will check the initial 3 entries are intact
             * This also validates contains with special characters
             * This test also highlighted the possibility of a negative hash result and has been patched
             */
            @Test
            @DisplayName("insert() Special characters in keys")
            void insertSpecialKeys() {

                // Check the special entries are there
                for (int i = 0; i < specialKeys.length; i++) {
                    assertTrue(hashTableCollisions.contains(specialKeys[i]), "insert() Special - should contain key with special characters: " + specialKeys[i]);
                    assertEquals("value" + i, hashTableCollisions.getString(specialKeys[i]), "insert() Special - should return correct value for special key: " + specialKeys[i]);
                }

                // Prints the table for manual validation
                hashTableCollisions.dump();

                // Check the original 3 are intact and count
                assertEquals(20, hashTableCollisions.count(), "insert() Special - count should be 20");
                assertTrue(hashTableCollisions.contains("test"),"insert() Special - should return true test 1");
                assertTrue(hashTableCollisions.contains("more"),"insert() Special - should return true test 2");
                assertTrue(hashTableCollisions.contains("again"),"insert() Special - should return true test 3");

                }

            /**
             * This tests it to make sure the delete functions handle the special characters
             * It will remove all the special character strings and validate they have been removed
             * It then will check the initial 3 entries are intact
             */
            @Test
            @DisplayName("delete() Special characters in keys")
            void DeleteSpecialKeys() {

                // Check the special entries are there
                for (String specialKey : specialKeys) {
                    hashTableCollisions.delete(specialKey);
                    assertFalse(hashTableCollisions.contains(specialKey), "delete() Special - should contain key with special characters: " + specialKey);
                }

                // Prints the table for manual validation
                hashTableCollisions.dump();

                // Check the original 3 are intact and count
                assertEquals(3, hashTableCollisions.count(), "delete() Special - count should be 3");
                assertTrue(hashTableCollisions.contains("test"), "delete() Special - should return true test 1");
                assertTrue(hashTableCollisions.contains("more"), "delete() Special - should return true test 2");
                assertTrue(hashTableCollisions.contains("again"), "delete() Special - should return true test 3");
            }
        }

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