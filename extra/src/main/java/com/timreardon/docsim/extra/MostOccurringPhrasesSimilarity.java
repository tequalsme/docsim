package com.timreardon.docsim.extra;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import com.timreardon.docsim.ExclusionList;
import com.timreardon.docsim.Similarity;
import com.timreardon.docsim.impl.DefaultExclusionList;
import com.timreardon.docsim.model.WordEntry;

public class MostOccurringPhrasesSimilarity implements Similarity {
    public static final int DEFAULT_PHRASE_SIZE = 3;
    public static final int DEFAULT_NUM_TOP_WORDS = 10;
    public static final int DEFAULT_MATCH_THRESHOLD = 5;

    private final int phraseSize;
    private final int numTopWords;
    private final int matchThreshold;
    private final ExclusionList exclusionList;

    public MostOccurringPhrasesSimilarity() {
        this(DEFAULT_PHRASE_SIZE, DEFAULT_NUM_TOP_WORDS, DEFAULT_MATCH_THRESHOLD);
    }

    public MostOccurringPhrasesSimilarity(int phraseSize, int numTopWords, int matchThreshold) {
        this(phraseSize, numTopWords, matchThreshold, new DefaultExclusionList());
    }

    public MostOccurringPhrasesSimilarity(int phraseSize, int numTopWords, int matchThreshold, ExclusionList exclusionList) {
        this.phraseSize = phraseSize;
        this.numTopWords = numTopWords;
        this.matchThreshold = matchThreshold;
        this.exclusionList = exclusionList;
    }

    @Override
    public boolean areSimilar(Scanner scanner1, Scanner scanner2) {
        Set<String> topWords1 = getTopWords(scanner1);
        Set<String> topWords2 = getTopWords(scanner2);

        return areSimilar(topWords1, topWords2);
    }

    private Set<String> getTopWords(Scanner scanner) {
        Map<String, WordEntry> wordsMap = new HashMap<String, WordEntry>();
        PriorityQueue<WordEntry> priorityQueue = new PriorityQueue<WordEntry>();

        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            if (exclusionList.shouldExclude(word)) {
                continue;
            }
            WordEntry entry = wordsMap.get(word);
            if (entry == null) {
                wordsMap.put(word, new WordEntry(word));
            } else {
                entry.incrementCount();
            }
        }

        priorityQueue.addAll(wordsMap.values());

        Set<String> topWords = new HashSet<String>(numTopWords);
        for (WordEntry entry : priorityQueue) {
            topWords.add(entry.getWord());
            if (topWords.size() == numTopWords) {
                break;
            }
        }
        return topWords;
    }

    private boolean areSimilar(Set<String> topWords1, Set<String> topWords2) {
        int commonElementCount = 0;
        for (String s : topWords1) {
            if (topWords2.contains(s)) {
                commonElementCount++;
            }
            if (commonElementCount >= matchThreshold) {
                break;
            }
        }

        return (commonElementCount >= matchThreshold);
    }
}
