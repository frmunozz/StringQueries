package experiments;

import suffixTree.SuffixTree;
import texthandler.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Experiment {
    private List<SuffixTree> trees;
    private List<Integer> hashCodes;
    private StringBuilder resultBuilder;

    public Experiment() {
        this.trees = new ArrayList<>();
        this.hashCodes = new ArrayList<>();
        this.resultBuilder = new StringBuilder();
    }

    public int count(String text, String word){
        SuffixTree tree = getTree(text);
        return 0;
    }

    public List<Integer> locate(String text, String word){
        SuffixTree tree = getTree(text);
        return null;
    }

    public List<String> topKQ(String text, int k, int q){
        SuffixTree tree = getTree(text);
        return null;
    }

    private SuffixTree recoverTree(int idx){
        return trees.get(idx);
    }

    private SuffixTree getTree(String text){
        int hc = text.hashCode();
        if (existTree(hc))
            return recoverTree(indexTree(hc));
        int ini = getTime();
        SuffixTree newTree = new SuffixTree(text);
        int end = getTime();
        resultBuilder.append(hc);
        resultBuilder.append(",");
        resultBuilder.append(end - ini);
        resultBuilder.append(",");
        hashCodes.add(hc);
        trees.add(newTree);
        return newTree;
    }

    private boolean existTree(int textHashCode){
        return hashCodes.contains(textHashCode);
    }

    private int indexTree(int textHashCode){
        return hashCodes.indexOf(textHashCode);
    }

    public void writeResult(String filename) throws IOException {
        FileReader.writeNewFile(resultBuilder.toString().getBytes(), filename);
    }

    public int getTime(){
        return 0;
    }
}
