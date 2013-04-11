package com.timreardon.docsim.exec;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.timreardon.docsim.impl.MostOccurringWordsSimilarity;

public class SimilarityExecTest {
    
    private ByteArrayOutputStream stdout, stderr;
    
    @Before
    public void setupClass() {
        stdout = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stdout));
        stderr = new ByteArrayOutputStream();
        System.setErr(new PrintStream(stderr));
    }

    @Test
    public void testNullArgs() {
        SimilarityExec.main(null);
        assertEquals("", stdout.toString());
        assertEquals(String.format("Usage: %s file1 file2\n", SimilarityExec.class.getName()), stderr.toString());
    }

    @Test
    public void testTooFewArgs() {
        SimilarityExec.main(new String[] {"xxx"});
        assertEquals("", stdout.toString());
        assertEquals(String.format("Usage: %s file1 file2\n", SimilarityExec.class.getName()), stderr.toString());
    }

    @Test
    public void testTooManyArgs() {
        SimilarityExec.main(new String[] {"xxx", "xxx", "xxx"});
        assertEquals("", stdout.toString());
        assertEquals(String.format("Usage: %s file1 file2\n", SimilarityExec.class.getName()), stderr.toString());
    }
    
    @Test
    public void test() {
        String fileName1 = "doc1.txt";
        String fileName2 = "doc2.txt";
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String file1 = cl.getResource(fileName1).getPath();
        String file2 = cl.getResource(fileName2).getPath();
        
        SimilarityExec.main(new String[] {file1, file2});
        
        String expectedMessage = String.format("Files '%s' and '%s' are not similar, as evaluated by %s\n",
                file1, file2, MostOccurringWordsSimilarity.class.getName());
        assertEquals(expectedMessage, stdout.toString());
        assertEquals("", stderr.toString());
    }
}
