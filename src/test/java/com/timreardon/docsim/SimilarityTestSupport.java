package com.timreardon.docsim;

import static com.timreardon.docsim.io.IoHelper.DELIMITER_REGEX;

import java.util.Scanner;

import org.junit.After;
import org.junit.Before;

public abstract class SimilarityTestSupport {
    protected static final String DOC1 = "This is a famous literary work, but I do not know who wrote it. That is strange since I am writing the\n"
            + "document. Very strange indeed, but many, many, many, many literary works are stranger. Who am I\n"
            + "kidding? I cannot even read.";

    protected static final String DOC2 = "This is a famous literary work, and the author is me. I have written many, many, many, many famous\n"
            + "books. However, I was not paid for any of the books, so I am poor.";

    protected Scanner scanner1, scanner2;

    @Before
    public final void setup() {
        scanner1 = new Scanner(DOC1);
        scanner1.useDelimiter(DELIMITER_REGEX);
        scanner2 = new Scanner(DOC2);
        scanner2.useDelimiter(DELIMITER_REGEX);
    }

    @After
    public final void teardown() {
        scanner1.close();
        scanner2.close();
    }
}
