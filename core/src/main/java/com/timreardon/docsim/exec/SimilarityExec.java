package com.timreardon.docsim.exec;

import java.util.Scanner;

import com.timreardon.docsim.Similarity;
import com.timreardon.docsim.impl.MostOccurringWordsSimilarity;
import com.timreardon.docsim.io.IoHelper;

// TODO be able to pass additional params as sys props (num words, etc.)
public class SimilarityExec {

    public static final String PROP_NAME = "similarity.impl";
    public static final String DEFAULT_IMPLEMENTATION = MostOccurringWordsSimilarity.class.getName();
    
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            usage();
        } else {
            evaluate(args[0], args[1]);
        }
    }
    
    private static void evaluate(String fileName1, String fileName2) {
        try {
            Similarity similarity = getImplementaion();
            Scanner scanner1 = IoHelper.createScanner(fileName1);
            Scanner scanner2 = IoHelper.createScanner(fileName2);
            boolean areSimilar = similarity.areSimilar(scanner1, scanner2);
            System.out.println(String.format("Files '%s' and '%s' are%s similar, as evaluated by %s",
                fileName1, fileName2, (areSimilar ? "" : " not"), similarity.getClass().getName()));

        } catch (Exception ex) {
            System.err.println("An unexected error occurred: " + ex.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private static Similarity getImplementaion() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String similarityClassName = System.getProperty(PROP_NAME, DEFAULT_IMPLEMENTATION);
        if (similarityClassName != null) {
            Class<Similarity> similarityClass = (Class<Similarity>) Class.forName(similarityClassName);
            if (!Similarity.class.isAssignableFrom(similarityClass)) {
                throw new RuntimeException(String.format("Custom Similarity implementation %s is not of type %s",
                    similarityClass, Similarity.class.getName()));
            }
            return similarityClass.newInstance();
        } else {
            throw new RuntimeException(String.format("System property '%s' undefined", PROP_NAME));
        }
    }

    private static final void usage() {
        System.err.println(String.format("Usage: %s file1 file2", SimilarityExec.class.getName()));
    }
}
