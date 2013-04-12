package com.timreardon.docsim.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import com.timreardon.docsim.model.TokenEntry;

public class MostOccurringPhrasesSimilarity extends AbstractTokenMatchingSimilarity {
    public static final int DEFAULT_PHRASE_SIZE = 3;
    public static final int DEFAULT_NUM_TOP_TOKENS = 20;
    public static final int DEFAULT_MATCH_THRESHOLD = 2;

    private final int phraseSize;

    public MostOccurringPhrasesSimilarity() {
        this(DEFAULT_PHRASE_SIZE);
    }

    public MostOccurringPhrasesSimilarity(int phraseSize) {
        this(phraseSize, DEFAULT_NUM_TOP_TOKENS, DEFAULT_MATCH_THRESHOLD);
    }

    public MostOccurringPhrasesSimilarity(int phraseSize, int numTopTokens, int matchThreshold) {
        super(numTopTokens, matchThreshold);
        this.phraseSize = phraseSize;
    }

    /**
     * Parses into the {@link #phraseSize} top-occurring phrases.
     * 
     * @see com.timreardon.docsim.impl.AbstractTokenMatchingSimilarity#parseTokens(java.util.Scanner)
     */
    @Override
    protected Collection<TokenEntry> parseTokens(Scanner scanner) {
        Map<String, TokenEntry> phraseMap = new HashMap<String, TokenEntry>();
        Queue<String> slidingWordQueue = new ArrayBlockingQueue<String>(phraseSize);

        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            if (exclusionList.shouldExclude(word)) {
                continue;
            }

            while (!slidingWordQueue.offer(word)) {
                slidingWordQueue.poll();
            }
            if (slidingWordQueue.size() != phraseSize) {
                continue;
            }
            String phrase = buildPhrase(slidingWordQueue);
            TokenEntry entry = phraseMap.get(phrase);
            if (entry == null) {
                phraseMap.put(phrase, new TokenEntry(phrase));
            } else {
                entry.incrementCount();
            }
        }

        return phraseMap.values();
    }

    private String buildPhrase(Queue<String> queue) {
        StringBuilder sb = new StringBuilder();
        for (String token : queue) {
            if (sb.length() != 0) {
                sb.append(' ');
            }
            sb.append(token);
        }
        return sb.toString();
    }
}
