package com.timreardon.docsim.impl;

import static com.timreardon.docsim.impl.MostOccurringWordsSimilarity.DEFAULT_NUM_TOP_WORDS;
import static com.timreardon.docsim.impl.MostOccurringWordsSimilarity.DELIMITER_REGEX;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class MostOccurringWordsSimilarityTest {
    private static final String DOC1 =
	"This is a famous literary work, but I do not know who wrote it. That is strange since I am writing the\n" +
	"document. Very strange indeed, but many, many, many, many literary works are stranger. Who am I\n" +
	"kidding? I cannot even read.";
    
    private static final String DOC2 =
	"This is a famous literary work, and the author is me. I have written many, many, many, many famous\n" +
	"books. However, I was not paid for any of the books, so I am poor.";
    
    private static Scanner SCANNER1, SCANNER2;
    
    @BeforeClass
    public static final void setupClass() {
	SCANNER1 = new Scanner(DOC1);
	SCANNER1.useDelimiter(DELIMITER_REGEX);
	SCANNER2 = new Scanner(DOC2);
	SCANNER2.useDelimiter(DELIMITER_REGEX);
    }
    
    @Test
    public void test() {
	MostOccurringWordsSimilarity similarity = new MostOccurringWordsSimilarity();
	assertFalse(similarity.areSimilar(SCANNER1, SCANNER2));
    }
    
    // TODO
    @Ignore("Need to fix Comparable/PriorityQueue")
    @Test
    public void testWithLoweredThreshold() {
	MostOccurringWordsSimilarity similarity = new MostOccurringWordsSimilarity(DEFAULT_NUM_TOP_WORDS, 1);
	assertTrue(similarity.areSimilar(SCANNER1, SCANNER2));
    }
}
