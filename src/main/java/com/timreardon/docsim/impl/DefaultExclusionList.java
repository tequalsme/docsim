package com.timreardon.docsim.impl;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.Set;

import com.timreardon.docsim.ExclusionList;

/**
 * {@link ExclusionList} implementation that uses a pre-defined {@link DefaultExclusionList#DEFAULT_WORDS set} of words,
 * or also allows a user-specified set to be used.
 */
public class DefaultExclusionList implements ExclusionList {
    public static final String[] DEFAULT_WORDS = { "i", "is", "the", "a", "are", "am" };

    private final Set<String> words;

    /**
     * Default constructor that uses a pre-defined {@link DefaultExclusionList#DEFAULT_WORDS set} of excluded words.
     */
    public DefaultExclusionList() {
        this.words = new HashSet<String>(asList(DEFAULT_WORDS));
    }

    /**
     * Constructor using a given set of words. Note that any normalization must be performed prior to calling.
     * 
     * @param words
     */
    public DefaultExclusionList(Set<String> words) {
        if (words == null) {
            throw new IllegalArgumentException("set must not be null");
        }
        this.words = words;
    }

    @Override
    public boolean shouldExclude(String word) {
        return words.contains(word);
    }

}
