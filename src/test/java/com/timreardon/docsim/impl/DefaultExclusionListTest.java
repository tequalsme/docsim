package com.timreardon.docsim.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import com.timreardon.docsim.ExclusionList;

public class DefaultExclusionListTest {

    private ExclusionList exclusionList = new DefaultExclusionList();

    @Test
    public void testExcludedWord() {
        assertTrue(exclusionList.shouldExclude("i"));
    }

    @Test
    public void testUnnormalizedWordDoesNotMatch() {
        assertFalse(exclusionList.shouldExclude("I"));
    }

    @Test
    public void testWordDoesNotMatch() {
        assertFalse(exclusionList.shouldExclude("other"));
    }

    @Test
    public void testNull() {
        assertFalse(exclusionList.shouldExclude(null));
    }

    @Test
    public void testEmpty() {
        assertFalse(exclusionList.shouldExclude(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAlternateSetIsNull() {
        new DefaultExclusionList(null);
    }

    @Test
    public void testAlternateSet() {
        ExclusionList exclusionList = new DefaultExclusionList(new HashSet<String>(Arrays.asList("new", "other")));
        assertTrue(exclusionList.shouldExclude("new"));
        assertFalse(exclusionList.shouldExclude("i"));
    }
}
