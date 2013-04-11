package com.timreardon.docsim.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import com.timreardon.docsim.ExclusionList;
import com.timreardon.docsim.Similarity;
import com.timreardon.docsim.core.DefaultExclusionList;
import com.timreardon.docsim.model.WordEntry;

public class MostOccurringWordsSimilarity implements Similarity {
    public static final int DEFAULT_NUM_TOP_WORDS = 10;
    public static final int DEFAULT_MATCH_THRESHOLD = 5;
    
    public static final String DELIMITER_REGEX = "\\W+";

    private final int numTopWords;
    private final int matchThreshold;
    private final ExclusionList exclusionList;

    public MostOccurringWordsSimilarity() {
	this(DEFAULT_NUM_TOP_WORDS, DEFAULT_MATCH_THRESHOLD);
    }

    public MostOccurringWordsSimilarity(int numTopWords, int matchThreshold) {
	this(numTopWords, matchThreshold, new DefaultExclusionList());
    }

    public MostOccurringWordsSimilarity(int numTopWords, int matchThreshold,
	    ExclusionList exclusionList) {
	this.numTopWords = numTopWords;
	this.matchThreshold = matchThreshold;
	this.exclusionList = exclusionList;
    }

    public boolean areSimilar(String fileName1, String fileName2) {
	BufferedReader reader1 = null, reader2 = null;
	try {
	    reader1 = new BufferedReader(new FileReader(fileName1));
	    Scanner scanner1 = createScanner(reader1);

	    reader2 = new BufferedReader(new FileReader(fileName2));
	    Scanner scanner2 = createScanner(reader2);

	    return areSimilar(scanner1, scanner2);

	} catch (IOException ex) {
	    System.err.println("Unable to process: " + ex.getMessage());
	    return false;
	} finally {
	    try {
		if (reader1 != null) {
		    reader1.close();
		}
		if (reader2 != null) {
		    reader2.close();
		}
	    } catch (IOException e) {
		// ignore
	    }
	}
    }
    
    private Scanner createScanner(Readable readable) {
	Scanner scanner = new Scanner(readable);
	scanner.useDelimiter(DELIMITER_REGEX);
	return scanner;
    }

    @Override
    public boolean areSimilar(Scanner scanner1, Scanner scanner2) {
	Set<String> topWords1 = getTopWords(scanner1);
	Set<String> topWords2 = getTopWords(scanner2);
	
	return areSimilar(topWords1, topWords2);
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
}
