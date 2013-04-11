package com.timreardon.docsim.model;

public class WordEntry implements Comparable<WordEntry> {

    private String word;
    private int count;

    public WordEntry(String word) {
        this.word = word;
        this.count = 1;
    }

    public void incrementCount() {
        count++;
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return word + "(" + count + ")";
    }

    @Override
    public int compareTo(WordEntry o) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (this == o) {
            return EQUAL;
        }

        if (this.count > o.count) {
            return BEFORE;
        }
        if (this.count < o.count) {
            return AFTER;
        }

        int comparison = this.word.compareTo(o.word);
        if (comparison != EQUAL) {
            return comparison;
        }

        return EQUAL;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + count;
        result = prime * result + ((word == null) ? 0 : word.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WordEntry other = (WordEntry) obj;
        if (count != other.count)
            return false;
        if (word == null) {
            if (other.word != null)
                return false;
        } else if (!word.equals(other.word))
            return false;
        return true;
    }
}
