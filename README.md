docsim
======

A Document Similarity Finder.


Problem
-------

Required Document Similarity Algorithm Description

Given two documents, similarity is determined by comparing the ten most occurring words. For each
document, determine the ten most occurring words. If the two documents have five or more words
common in the set of ten most occurring words, then the documents are similar. Otherwise, the documents
are not similar. When two different words occur the same amount of times, then the words are ordered
alphabetically in the list of most occurring words. Since some words are less indicative of one’s writing
style (e.g., I, is, the, a), an Exclusion List may be supplied containing words to exclude from the list of most
occurring words.

Requirements
------------

1) No third-party libraries may used (e.g., Spring, Apache Commons).
2) The document similarity algorithm should be pluggable. 
3) Two different similarity algorithms must be implemented. One of the algorithms is described
above and the other one is of your choosing.

Second Implementation
---------------------

The second implementation evaluates the N most occurring *phrases*. It is similar to the word algorithm described above,
but uses a sliding phrase window with a default length of 3 words.

Assumptions
-----------

1. Additional configuration parameters could be exposed to the command line interface as system properties. (i.e.,
numTopWords, matchThreshold, phraseSize.) Such parameters are available on the implementations but not exposed to the
command line interface. Exposing these would require additional effort that does not seem pertinent to the exercise goals.

Usage Examples
--------------

java -jar target/docsim-${version}.jar src/test/resources/doc1.txt src/test/resources/doc2.txt

java -Dsimilarity.impl=com.timreardon.docsim.impl.MostOccurringPhrasesSimilarity -jar target/docsim-${version}.jar src/test/resources/doc1.txt src/test/resources/doc2.txt
