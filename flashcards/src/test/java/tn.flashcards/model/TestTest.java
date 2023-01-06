package tn.flashcards.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTest {
    @Test public void testBase() {
        assertEquals(1, 1);
        assertEquals("a", "a");
    }

    @Test(expected  = IndexOutOfBoundsException.class)
    public void testException() {
        new java.util.ArrayList<Object>().get(0);
    }
}
