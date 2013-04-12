package com.timreardon.docsim.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import com.timreardon.docsim.ExclusionList;
import com.timreardon.docsim.Similarity;
import com.timreardon.docsim.model.TokenEntry;

public abstract class AbstractTokenMatchingSimilarity implements Similarity {
    public static final int DEFAULT_NUM_TOP_TOKENS = 10;
    public static final int DEFAULT_MATCH_THRESHOLD = 5;

    protected final int numTopTokens;
    protected final int matchThreshold;
    protected ExclusionList exclusionList = new DefaultExclusionList();

    public AbstractTokenMatchingSimilarity() {
        this(DEFAULT_NUM_TOP_TOKENS, DEFAULT_MATCH_THRESHOLD);
    }

    public AbstractTokenMatchingSimilarity(int numTopTokens, int matchThreshold) {
        this.numTopTokens = numTopTokens;
        this.matchThreshold = matchThreshold;
    }

    public void setExclusionList(ExclusionList exclusionList) {
        this.exclusionList = exclusionList;
    }

    @Override
    public boolean areSimilar(Scanner scanner1, Scanner scanner2) {
        Collection<TokenEntry> entries1 = parseTokens(scanner1);
        Collection<TokenEntry> entries2 = parseTokens(scanner2);

        Set<String> topTokens1 = limitTokens(entries1);
        Set<String> topTokens2 = limitTokens(entries2);

        return intersection(topTokens1, topTokens2);
    }

    /**
     * Parse into tokens as specified by algorithm.
     * 
     * @param scanner
     * @return Collection of tokens
     */
    protected abstract Collection<TokenEntry> parseTokens(Scanner scanner);

    protected Set<String> limitTokens(Collection<TokenEntry> entries) {
        PriorityQueue<TokenEntry> priorityQueue = new PriorityQueue<TokenEntry>(entries);

        Set<String> topTokens = new HashSet<String>(numTopTokens);
        for (TokenEntry entry : priorityQueue) {
            topTokens.add(entry.getToken());
            if (topTokens.size() == numTopTokens) {
                break;
            }
        }
        return topTokens;
    }

    /**
     * Determines whether intersection of two supplied sets is >= {@link #matchThreshold}, breaking early if threshold
     * is reached.
     * 
     * @param tokens1
     * @param tokens2
     * @return true if the common elements between the two sets is >= <code>matchThreshold</code>
     */
    protected boolean intersection(Set<String> tokens1, Set<String> tokens2) {
        int commonElementCount = 0;
        for (String s : tokens1) {
            if (tokens2.contains(s)) {
                commonElementCount++;
            }
            if (commonElementCount >= matchThreshold) {
                break;
            }
        }

        return (commonElementCount >= matchThreshold);
    }
}
