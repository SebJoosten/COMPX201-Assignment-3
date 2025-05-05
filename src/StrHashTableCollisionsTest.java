import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


class StrHashTableCollisionsTest {

    @Test
    @DisplayName("Test - if the insert works")
    void insert() {
        StrHashTableCollisions sth = new StrHashTableCollisions();
        sth.insert("test","this is a test");
        assertTrue(sth.contains("test"),"Insert test should return true");
    }

    @Test
    @DisplayName("Test - if insert null is caught and handled correctly ")
    void insertNull() {
        StrHashTableCollisions sth = new StrHashTableCollisions();
        sth.insert(null,null);
        assertFalse(sth.contains("test"),"Insert null test should return False");
    }

    @Test
    @DisplayName("Test - make sure contains is only true when contains")
    void insertContains() {

        StrHashTableCollisions sth = new StrHashTableCollisions();
        assertFalse(sth.contains("test"),"Contains before insert test should return False");
        sth.insert("test","this is a test");
        assertTrue(sth.contains("test"),"Contains after insert test should return True");
        assertFalse(sth.contains("something"),"Contains \"something\" should return False");

    }

    @Test
    @DisplayName("Test - if contains works with more then one set of items ")
    void insert3Contains3() {

        StrHashTableCollisions sth = new StrHashTableCollisions();
        sth.insert("test","this is a test");
        sth.insert("more","this is another test");
        sth.insert("again","this is again a test");
        assertTrue(sth.contains("test"),"Insert 1 test should return True");
        assertTrue(sth.contains("more"),"Insert 2 test should return True");
        assertTrue(sth.contains("again"),"Insert 3 test should return True");

    }

    @Test
    @DisplayName("Test - if delete works with more than one also checks contains again")
    void delete3() {

        StrHashTableCollisions sth = new StrHashTableCollisions();
        sth.insert("test","this is a test");
        sth.insert("more","this is another test");
        sth.insert("again","this is again a test");
        assertTrue(sth.contains("test"),"Insert 1 test should return True");
        assertTrue(sth.contains("more"),"Insert 2 test should return True");
        assertTrue(sth.contains("again"),"Insert 3 test should return True");
        sth.delete("test");
        assertFalse(sth.contains("test"),"Delete 1 test should return False");
        sth.delete("more");
        assertFalse(sth.contains("more"),"Delete 2 test should return False");
        sth.delete("again");
        assertFalse(sth.contains("again"),"Delete 3 test should return False");

    }

    @Test
    @DisplayName("Test - if contains handles null input and returns false")
    void containsNull() {

        StrHashTableCollisions sth = new StrHashTableCollisions();
        assertFalse(sth.contains(null),"Contains null test should return False and move on");

    }

    @Test
    @DisplayName("Test - get string works with multiply inputs and returns the correct string")
    void getString() {

        StrHashTableCollisions sth = new StrHashTableCollisions();
        sth.insert("test","this is a test");
        sth.insert("more","this is another test");
        sth.insert("again","this is again a test");
        assertEquals("this is a test",sth.getString("test"), "getString 1 should return 'this is a test'");
        assertEquals("this is another test",sth.getString("more"), "getString 2 should return 'this is another test'");
        assertEquals("this is again a test",sth.getString("again"), "getString 3 should return 'this is again a test'");

    }

    @Test
    @DisplayName("Test - if isEmpty returns correctly before and after insert and delete")
    void isEmpty() {

        StrHashTableCollisions sth = new StrHashTableCollisions();
        assertTrue(sth.isEmpty(),"isEmpty before insert should return True");
        sth.insert("test","this is a test");
        assertFalse(sth.isEmpty(),"isEmpty after insert should return False");
        sth.delete("test");
        assertTrue(sth.isEmpty(),"isEmpty after delete should return True");

    }

    @Test
    @DisplayName("Test - dose count return correctly before and after delete")
    void count() {

        StrHashTableCollisions sth = new StrHashTableCollisions();
        sth.insert("test","this is a test");
        sth.insert("more","this is another test");
        sth.insert("again","this is again a test");
        assertEquals(3, sth.count(),"Count incorrect before delete");
        sth.delete("test");
        assertEquals(2, sth.count(),"Count incorrect after delete 1 should be 2");
        sth.delete("more");
        assertEquals(1, sth.count(),"Count incorrect after delete 2 should be 1");
        sth.delete("again");
        assertEquals(0, sth.count(),"Count incorrect after delete 3 should be 0");

    }

    @Test
    void dump() {
    }
}