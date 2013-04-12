package com.timreardon.docsim.exec;

import static com.timreardon.docsim.exec.SimilarityExec.PROP_NAME;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.timreardon.docsim.impl.MostOccurringPhrasesSimilarity;
import com.timreardon.docsim.impl.MostOccurringWordsSimilarity;

public class SimilarityExecTest {

    private static String FILE1, FILE2;

    private ByteArrayOutputStream stdout, stderr;

    @BeforeClass
    public static final void setupClass() {
        String fileName1 = "doc1.txt";
        String fileName2 = "doc2.txt";
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        FILE1 = cl.getResource(fileName1).getPath();
        FILE2 = cl.getResource(fileName2).getPath();
    }

    @Before
    public void setup() {
        stdout = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stdout));
        stderr = new ByteArrayOutputStream();
        System.setErr(new PrintStream(stderr));
    }

    @After
    public void teardown() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testNullArgs() {
        SimilarityExec.main(null);
        assertError(String.format("Usage: %s file1 file2\n", SimilarityExec.class.getName()));
    }

    @Test
    public void testTooFewArgs() {
        SimilarityExec.main(new String[] { "xxx" });
        assertError(String.format("Usage: %s file1 file2\n", SimilarityExec.class.getName()));
    }

    @Test
    public void testTooManyArgs() {
        SimilarityExec.main(new String[] { "xxx", "xxx", "xxx" });
        assertError(String.format("Usage: %s file1 file2\n", SimilarityExec.class.getName()));
    }

    @Test
    public void test() {
        SimilarityExec.main(new String[] { FILE1, FILE2 });
        assertComparison(false, MostOccurringWordsSimilarity.class.getName());
    }

    @Test
    public void testWithAltImpl() {
        System.setProperty(PROP_NAME, MostOccurringPhrasesSimilarity.class.getName());
        SimilarityExec.main(new String[] { FILE1, FILE2 });
        assertComparison(true, MostOccurringPhrasesSimilarity.class.getName());
    }

    private void assertComparison(boolean areSimilar, String className) {
        String expectedMessage = String.format("Files '%s' and '%s' are%s similar, as evaluated by %s\n", FILE1, FILE2,
                (areSimilar ? "" : " not"), className);
        assertEquals(expectedMessage, stdout.toString());
        assertEquals("", stderr.toString());
    }

    private void assertError(String expectedStdErr) {
        assertEquals(expectedStdErr, stderr.toString());
        assertEquals("", stdout.toString());
    }
}
