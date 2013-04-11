package com.timreardon.docsim.impl;

import static com.timreardon.docsim.impl.MostOccurringWordsSimilarity.DEFAULT_NUM_TOP_WORDS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.timreardon.docsim.SimilarityTestSupport;

public class MostOccurringWordsSimilarityTest extends SimilarityTestSupport {

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
