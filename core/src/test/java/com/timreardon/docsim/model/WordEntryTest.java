package com.timreardon.docsim.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WordEntryTest {

    @Test
    public void testCompareTo() {
        WordEntry entry1 = new WordEntry("abc");
        WordEntry entry2 = new WordEntry("def");
        WordEntry entry3 = new WordEntry("abc");
        assertTrue(entry1.compareTo(entry2) < 0);
        assertFalse(entry2.compareTo(entry1) < 0);
        assertEquals(0, entry1.compareTo(entry3));
        
        entry2.incrementCount();
        assertFalse(entry1.compareTo(entry2) < 0);
        assertTrue(entry2.compareTo(entry1) < 0);
        
        entry1.incrementCount();
        assertTrue(entry1.compareTo(entry2) < 0);
        assertFalse(entry2.compareTo(entry1) < 0);
        assertTrue(entry1.compareTo(entry3) < 0);
        assertFalse(entry3.compareTo(entry1) < 0);
    }
    
    @Test
    public void testEqualsAndHashCode() {
        WordEntry entry1 = new WordEntry("abc");
        WordEntry entry2 = new WordEntry("abc");
        assertTrue(entry1.equals(entry2));
        assertTrue(entry2.equals(entry1));
        assertEquals(entry1.hashCode(), entry2.hashCode());
        
        entry1.incrementCount();
        assertFalse(entry1.equals(entry2));
        assertFalse(entry2.equals(entry1));
        assertFalse(entry1.hashCode() == entry2.hashCode());
    }
}
