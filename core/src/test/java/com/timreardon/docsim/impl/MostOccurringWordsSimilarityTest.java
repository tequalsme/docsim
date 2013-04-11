package com.timreardon.docsim.impl;

import static com.timreardon.docsim.impl.MostOccurringWordsSimilarity.DEFAULT_NUM_TOP_WORDS;
import static com.timreardon.docsim.impl.MostOccurringWordsSimilarity.DELIMITER_REGEX;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MostOccurringWordsSimilarityTest {
    private static final String DOC1 = "This is a famous literary work, but I do not know who wrote it. That is strange since I am writing the\n"
            + "document. Very strange indeed, but many, many, many, many literary works are stranger. Who am I\n"
            + "kidding? I cannot even read.";

    private static final String DOC2 = "This is a famous literary work, and the author is me. I have written many, many, many, many famous\n"
            + "books. However, I was not paid for any of the books, so I am poor.";

    private Scanner scanner1, scanner2;

    @Before
    public final void setup() {
        scanner1 = new Scanner(DOC1);
        scanner1.useDelimiter(DELIMITER_REGEX);
        scanner2 = new Scanner(DOC2);
        scanner2.useDelimiter(DELIMITER_REGEX);
    }
    
    @After
    public final void teardown() {
        scanner1.close();
        scanner2.close();
    }

    @Test
    public void test() {
        MostOccurringWordsSimilarity similarity = new MostOccurringWordsSimilarity();
        assertFalse(similarity.areSimilar(scanner1, scanner2));
    }

    @Test
    public void testWithLoweredThreshold() {
        MostOccurringWordsSimilarity similarity = new MostOccurringWordsSimilarity(DEFAULT_NUM_TOP_WORDS, 1);
        assertTrue(similarity.areSimilar(scanner1, scanner2));
    }
}
