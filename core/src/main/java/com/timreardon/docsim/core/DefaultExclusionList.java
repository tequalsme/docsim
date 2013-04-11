package com.timreardon.docsim.core;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.Set;

import com.timreardon.docsim.ExclusionList;

public class DefaultExclusionList implements ExclusionList {
    public static final String[] DEFAULT_WORDS = { "i", "is", "the", "a", "are", "am" };
    
    private final Set<String> words;

    public DefaultExclusionList() {
	this.words = new HashSet<String>(asList(DEFAULT_WORDS));
    }

    public DefaultExclusionList(Set<String> words) {
	// TODO normalize list
	this.words = words;
    }

    @Override
    public boolean shouldExclude(String word) {
	return words.contains(word);
    }

}
