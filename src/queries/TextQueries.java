package queries;

import oldsuffix.OldSuffixTree;

import java.util.ArrayList;
import java.util.List;

public class TextQueries {
    private List<OldSuffixTree> trees;
    private List<String> texts;

    public TextQueries(String text) {
        trees = new ArrayList<>();
        texts = new ArrayList<>();
    }

    private boolean isComputed(String text) {
        return texts.contains(text);
    }

    private int treeIndex(String text) {
        return texts.indexOf(text);
    }

    private void addTree(OldSuffixTree tree, String text) {
        trees.add(tree);
        texts.add(text);
    }

    private OldSuffixTree getTree(String text) {
        OldSuffixTree tree;
        if (isComputed(text)) {
            tree = trees.get(treeIndex(text));
        }
        else {
            tree = new OldSuffixTree(text);
            addTree(tree, text);
        }
        return tree;
    }

//    queries

    /**
     * get the number of positions where the string 'str' occurs in the text 'text'
     *
     * @param str the string used in the query
     * @param text the text used to perform the query
     * @return the number of positions where the match occurs
     */
    public int count(String str, String text) {
        OldSuffixTree tree = getTree(text);

        return 0;
    }

    /**
     * gives a list with the positions where the string 'str' occurs in the text 'text'
     *
     * @param text the text used to perform the query
     * @param str the string used in the query
     * @return List with the positions where the match occurs
     */
    public List<Integer> locate(String text, String str) {
        OldSuffixTree tree = getTree(text);

        return null;
    }

    /**
     * gives a list with the 'k' strings of length 'q' that is repeated more times in the text 'text'
     *
     * @param text the text used to perform the query
     * @param k the number of strings that we are looking for
     * @param q the length of the strings that we are looking for
     * @return List with the founded strings.
     */
    public List<String> topKQ(String text, int k, int q) {
        OldSuffixTree tree = getTree(text);

        return null;
    }
}
