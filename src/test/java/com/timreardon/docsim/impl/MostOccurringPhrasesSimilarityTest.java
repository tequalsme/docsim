package com.timreardon.docsim.impl;

import static com.timreardon.docsim.impl.MostOccurringPhrasesSimilarity.DEFAULT_NUM_TOP_TOKENS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.timreardon.docsim.Similarity;
import com.timreardon.docsim.SimilarityTestSupport;

public class MostOccurringPhrasesSimilarityTest extends SimilarityTestSupport {

    @Test
    public void test() {
        Similarity similarity = new MostOccurringPhrasesSimilarity();
        assertTrue(similarity.areSimilar(scanner1, scanner2));
    }

    @Test
    public void testWithSlightlyRaisedPhraseSize() {
        Similarity similarity = new MostOccurringPhrasesSimilarity(4, DEFAULT_NUM_TOP_TOKENS, 1);
        assertTrue(similarity.areSimilar(scanner1, scanner2));
    }

    @Test
    public void testWithHigherRaisedPhraseSize() {
        Similarity similarity = new MostOccurringPhrasesSimilarity(5, DEFAULT_NUM_TOP_TOKENS, 1);
        assertFalse(similarity.areSimilar(scanner1, scanner2));
    }
}
