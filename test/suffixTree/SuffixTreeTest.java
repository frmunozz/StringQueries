package suffixTree;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import texthandler.FileReader;
import texthandler.TextHandler;

import java.util.List;

import static org.junit.Assert.*;

public class SuffixTreeTest {
    private static SuffixTree basicTree;
    private static SuffixTree mediumTree;
    private static String largeStr;
    private static SuffixTree largeTree;
    private static SuffixTree extremelyLargeTree;

    @BeforeClass
    public static void setUp() throws Exception {
        basicTree = new SuffixTree("banana$");
        mediumTree = new SuffixTree(TextHandler.preProcesing("This is a big new idea$"));
        largeStr = "   This is a           big   new idea,  it mix \nseveral other ideas in one \tmacroscopic Thesis";
        largeStr = TextHandler.preProcesing(largeStr) + "$";
        largeTree = new SuffixTree(largeStr);

        String file = TextHandler.english();
        String text = FileReader.readText(file, 0, (int)Math.pow(2, 23));
        text = TextHandler.preProcesing(text) + "$";
//        FileReader.writeNewFile(text.getBytes(), "2pow23English.txt");
        extremelyLargeTree = new SuffixTree(text);

    }

    @Test
    public void search() {
        assertTrue(basicTree.search("a"));
        assertTrue(basicTree.search("banana"));
        assertTrue(basicTree.search("banana"));
        assertTrue(basicTree.search("na"));
        assertFalse(basicTree.search("bananabanana"));

        assertTrue(mediumTree.search("this"));
        assertTrue(mediumTree.search("this is a"));
        assertTrue(mediumTree.search("big new idea"));
        assertTrue(mediumTree.search("s"));
        assertFalse(mediumTree.search("z"));
        assertFalse(mediumTree.search("thisis"));

        assertTrue(largeTree.search("new idea it mix"));
        assertTrue(largeTree.search("a big new"));
        assertTrue(largeTree.search("mix several other"));
        assertTrue(largeTree.search("ral oth"));
        assertFalse(largeTree.search("new idea, it mix"));

        assertTrue(extremelyLargeTree.search("etext"));
        assertTrue(extremelyLargeTree.search("to keep themselves alive and repaired to a woman whom she had known in former days when she came in to her and she saw her case she rose and receiving her kindly wept"));

    }

    @Test
    public void count() {
        assertEquals(3, basicTree.count("a"));
        assertEquals(2, basicTree.count("na"));
        assertEquals(0, basicTree.count("z"));

        assertEquals(1, mediumTree.count("t"));
        assertEquals(2, mediumTree.count("is"));
        assertEquals(0, mediumTree.count("abignew"));
        assertEquals(1, mediumTree.count("a big new"));

        assertEquals(10, largeTree.count("i"));
        assertEquals(3, largeTree.count("is"));
        assertEquals(1, largeTree.count("thesis"));
        assertEquals(1, largeTree.count("one macroscopic"));
        assertEquals(0, largeTree.count("z"));
        assertEquals(3, largeTree.count("h"));

        assertEquals(8550, extremelyLargeTree.count("this"));
        assertEquals(87, extremelyLargeTree.count("kindly"));
    }

    @Test
    public void locate() {
        List<Integer> r;
        r = basicTree.locate("na");
        assertTrue(r.contains(2));
        assertTrue(r.contains(4));
        assertEquals(2, r.size());
        r = basicTree.locate("babana");
        assertTrue(r.isEmpty());

        r = mediumTree.locate("e");
        assertTrue(r.contains(15));
        assertTrue(r.contains(20));
        assertEquals(2, r.size());
        r = mediumTree.locate("This");
        assertTrue(r.isEmpty());

        r = largeTree.locate(" th");
        assertEquals(1, r.size());
        int p = r.get(0);
        assertEquals(" th", largeStr.substring(p, p + 3));
        r = largeTree.locate(" i");
        assertEquals(5, r.size());
        for (int i : r){
            assertEquals(" i", largeStr.substring(i, i + 2));
        }

        r = extremelyLargeTree.locate("bolshevism");
        System.out.println(r);
        assertEquals(14, r.size());
        assertTrue(r.contains(3650));
    }

    @Test
    public void topKQ() {
        List<String> r;
        r = basicTree.topKQ(3, 2);
        assertTrue(r.contains("na"));
        assertTrue(r.contains("an"));
        assertTrue(r.contains("ba"));
        assertEquals(3, r.size());
        r = basicTree.topKQ(2, 3);
        assertTrue(r.contains("ana"));
        assertTrue(r.contains("nan") || r.contains("ban"));
        assertEquals(2, r.size());
        r = basicTree.topKQ(2, 6);
        assertTrue(r.contains("banana"));
        assertEquals(1, r.size());

        r = mediumTree.topKQ(3, 7);
        System.out.println(r);
        assertEquals(3, r.size());
//         we cannot know what string we found since there are no string of length 7 repeated
        for (String s : r)
            assertEquals(7, s.length());

        r = largeTree.topKQ(2, 5);
        assertEquals(2, r.size());
        assertTrue(r.contains(" idea"));
        for (String s : r)
            assertEquals(5, s.length());

        r = largeTree.topKQ(3, largeStr.length() - 1); // we rest the '$'
        System.out.println(r);
        assertEquals(1, r.size());

        r = extremelyLargeTree.topKQ(10, 20);
        System.out.println(r);
        assertTrue(r.contains(" permitted say when "));
        for (String s : r)
            assertEquals(20, s.length());
    }
}