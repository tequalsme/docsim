package com.timreardon.docsim.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.timreardon.docsim.model.TokenEntry;

public class MostOccurringWordsSimilarity extends AbstractTokenMatchingSimilarity {

    public MostOccurringWordsSimilarity() {
        super();
    }

    public MostOccurringWordsSimilarity(int numTopWords, int matchThreshold) {
        super(numTopWords, matchThreshold);
    }

    /**
     * Parses into the {@link AbstractTokenMatchingSimilarity#numTopTokens} top-occurring words.
     * 
     * @see com.timreardon.docsim.impl.AbstractTokenMatchingSimilarity#parseTokens(java.util.Scanner)
     */
    @Override
    protected Collection<TokenEntry> parseTokens(Scanner scanner) {
        Map<String, TokenEntry> wordsMap = new HashMap<String, TokenEntry>();

        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            if (exclusionList.shouldExclude(word)) {
                continue;
            }
            TokenEntry entry = wordsMap.get(word);
            if (entry == null) {
                wordsMap.put(word, new TokenEntry(word));
            } else {
                entry.incrementCount();
            }
        }

        return wordsMap.values();
    }
}
