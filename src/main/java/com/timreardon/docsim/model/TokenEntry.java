package com.timreardon.docsim.model;

public class TokenEntry implements Comparable<TokenEntry> {

    private String token;
    private int count;

    public TokenEntry(String token) {
        this.token = token;
        this.count = 1;
    }

    public void incrementCount() {
        count++;
    }

    public String getToken() {
        return token;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return token + "(" + count + ")";
    }

    @Override
    public int compareTo(TokenEntry o) {
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

        int comparison = this.token.compareTo(o.token);
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
        result = prime * result + ((token == null) ? 0 : token.hashCode());
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
        TokenEntry other = (TokenEntry) obj;
        if (count != other.count)
            return false;
        if (token == null) {
            if (other.token != null)
                return false;
        } else if (!token.equals(other.token))
            return false;
        return true;
    }
}
