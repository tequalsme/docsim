package com.timreardon.docsim;

/**
 * API for excluded words from a calculation or algorithm.
 */
public interface ExclusionList {

    /**
     * Whether the given word should be excluded.
     * 
     * @param word
     * @return true if the word should be excluded
     */
    boolean shouldExclude(String word);
}
