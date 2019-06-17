package optimalsuffix;

import org.junit.Before;
import org.junit.Test;
import texthandler.FileReader;
import texthandler.TextHandler;

import java.io.IOException;

import static org.junit.Assert.*;

public class SuffixTreeTest {
    SuffixTree tree;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void basic(){
        String text = "banana";
        SuffixTree tree = new SuffixTree(TextHandler.preProcesing(text) + "$");
        tree.visualize();
        assertTrue(tree.search("a"));
        assertTrue(tree.search("na"));
        assertTrue(tree.search("ba"));
        assertTrue(tree.search("bana"));
        assertTrue(tree.search("nana"));
        assertTrue(tree.search("anana"));
        assertTrue(tree.search("banan"));
        assertFalse(tree.search("abana"));
        assertTrue(tree.search("banana$"));
        assertTrue(tree.search("banana"));
        assertFalse(tree.search("nanana"));
    }

    @Test
    public void medium() {
        String text = "This is a big new idea,";
        SuffixTree tree = new SuffixTree(TextHandler.preProcesing(text) + "$");
        assertTrue(tree.search("this"));
        assertTrue(tree.search("isabig"));
        assertTrue(tree.search("newidea"));
        assertTrue(tree.search("thisisabignewidea"));
        assertTrue(tree.search("thisisabignewidea$"));
        assertFalse(tree.search("thisisabad"));
        assertFalse(tree.search("it"));
        assertFalse(tree.search("c"));
    }

    @Test
    public void search() {
        String text = "This is a big new idea, it mix \nseveral other ideas in one \tmacroscopic Thesis";
//        String text = "This is a big new idea, it mix \nseveral";
//        System.out.println(TextHandler.preProcesing(text) + "$");
        SuffixTree tree = new SuffixTree(TextHandler.preProcesing(text) + "$");
//        tree.visualize();
        assertTrue(tree.search("this"));
        assertTrue(tree.search("itmixseveral"));
        assertTrue(tree.search("thisisabignewideaitmixseveralotherideasinone"));
        assertTrue(tree.search("a"));
        assertFalse(tree.search("z"));
        assertFalse(tree.search("thisisabadidea"));
    }

//    @Test
//    public void count() {
//        String text = "This is a big new idea, it mix \nseveral other ideas in one \tmacroscopic Thesis";
////        String text = "This is a big new idea, it mix \nseveral";
////        System.out.println(TextHandler.preProcesing(text) + "$");
//        SuffixTree tree = new SuffixTree(TextHandler.preProcesing(text) + "$");
//        assertEquals(3, tree.count("is"));
//        assertEquals(10, tree.count("i"));
//        assertEquals(2, tree.count("idea"));
//        assertEquals(1, tree.count("thesis"));
//        assertEquals(0, tree.count("notintext"));
//        assertEquals(0, tree.count("isz"));
//    }

    @Test
    public void bigSearch() throws IOException {
        String file = TextHandler.english();
        FileReader reader = new FileReader(file);
        System.out.println("length file in bites: " + reader.getLenght());
        System.out.println("2^18=" + Math.pow(2, 18));
        String text = reader.readText(0, (int)Math.pow(2, 23));
//        System.out.println(TextHandler.preProcesing(text) + "$");
        SuffixTree tree = new SuffixTree(TextHandler.preProcesing(text) + "$");
//        tree.visualize();
        assertTrue(tree.search("redactor"));
        assertTrue(tree.search("footnotes"));
        assertTrue(tree.search("revolutionary"));
    }
}