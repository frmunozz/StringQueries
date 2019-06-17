package oldsuffix;

import texthandler.FileReader;
import texthandler.TextHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OldSuffixTree {
    private List<OldNode> oldNodes;

    public OldSuffixTree(String text) {
        oldNodes = new ArrayList<>();
        oldNodes.add(new OldNode());
        for (int i = 0; i < text.length(); i++) {
            addSuffix(text.substring(i));
//            System.out.println("adding oldsuffix: <" + text.substring(i) + ">");
//            System.out.println("==========");
//            visualize();
//            System.out.println("===========");
        }
    }

    private void addSuffix(String suffix) {
        int n = 0;
        int i = 0;
        while (i < suffix.length()) {
            char b = suffix.charAt(i);
            List<Integer> children = oldNodes.get(n).getChilden();
            int x2 = 0;
            int n2;
            while (true) {
                if (x2 == children.size()) {
                    n2 = oldNodes.size();
                    OldNode tmp = new OldNode(suffix.substring(i));
                    oldNodes.add(tmp);
//                    oldNodes.get(n).increaseSuffixCounter();
                    children.add(n2);
                    return;
                }
                n2 = children.get(x2);
                if (oldNodes.get(n2).getSub().length() > 0 && oldNodes.get(n2).getSub().charAt(0) == b)
                    break;
                x2++;
            }
            String sub2 = oldNodes.get(n2).getSub();
            int j = 0;
            while (j < sub2.length()) {
                if (suffix.charAt(i + j) != sub2.charAt(j)) {
                    int n3 = n2;
                    n2 = oldNodes.size();
                    OldNode tmp = new OldNode(sub2.substring(0, j));
                    tmp.addChild(n3);
                    oldNodes.add(tmp);
                    oldNodes.get(n3).setSub(sub2.substring(j));
                    oldNodes.get(n).setChild(x2, n2);
                    break;
                }
                j++;
            }
            i += j;
            n = n2;
        }
    }

    public void visualize() {
        if (oldNodes.isEmpty()) {
            System.out.println("<empty>");
            return;
        }
        visualize_f(0, "");
    }

    private void visualize_f(int n, String pre) {
        List<Integer> children = oldNodes.get(n).getChilden();
        if (children.isEmpty()) {
            System.out.println("- " + oldNodes.get(n).getSub());
            return;
        }
        System.out.println("┐ " + oldNodes.get(n).getSub());
        for (int i = 0; i <  children.size() - 1; i++) {
            int c = children.get(i);
            System.out.print(pre + "├─");
            visualize_f(c, pre + "│ ");
        }
        System.out.print(pre + "└─");
        visualize_f(children.get(children.size() - 1), pre + "  ");
    }

    public boolean search(String str) {
        return innerSearch(str, 0);
    }

    private boolean innerSearch(String str, int n) {
        System.out.println("searching <" + str + "> from OldNode: " + n);
        List<Integer> childern = oldNodes.get(n).getChilden();
        if (childern.isEmpty()) {
            System.out.println("OldNode: " + n + "doesnt have children");
            return false;
        }
        if (str.isEmpty()) {
            System.out.println("string is a preffix and we already found him in OldNode: " + n);
            return true;
        }
        for (int i = 0; i < childern.size() - 1; i++) {
            int c = childern.get(i);
            String sub = oldNodes.get(c).getSub();
            System.out.println(":: searching on child: " + c + " with sub <" + sub + ">");
            if (sub.length() > str.length()) {
                System.out.println(":::: <" + sub + "> is larger than <" + str + ">, so we need to find prefix here");
                if (sub.substring(0, str.length()).equals(str)) {
                    System.out.println(":::::: sub.substr <" + sub.substring(0, str.length()) + "> match with str <" + str + ">!");
                    return true;
                }
                System.out.println(":::::: sub.substr <" + sub.substring(0, str.length()) + "> doesnt match with str <" + str + ">, sowe continue searching");
            }
            else {
                if (str.substring(0, sub.length()).equals(sub)) {
                    System.out.println(":::: <" + sub + "> is smaller than <" + str + ">, so we descend to continue searching");
                    return innerSearch(str.substring(sub.length()), c);
                }
            }
        }
        System.out.println(":: none of the existing oldNodes satisfied the str");
        return false;
    }

//    private int innerCount(String str, int n) {
//        OldNode node  = oldNodes.get(n);
//        if (node.isLeaf()) {
//            return node.matchPrefix(str) ? 1 : 0;
//        }
//        if (str.isEmpty()) {
//            return 0;
//        }
//        int count = 0;
//        for (int i = 0; i < node.numberOfChilder() - 1; i++) {
//            int c = node.getChild(i);
//            if (node.matchString(str)) {
//                count +=  innerCount(str.substring(node.getSub().length()), c, count);
//            }
//        }
//    }

    public static void main(String[] args) throws IOException {
//        OldSuffixTree tree = new OldSuffixTree("thisisatesttext$");
//        tree.visualize();
//        System.out.println(tree.search("test"));
//        System.out.println("------------------");
//        System.out.println(tree.search("text"));
//        System.out.println("------------------");
//        System.out.println(tree.search("is"));
//        System.out.println("------------------");
//        System.out.println(tree.search("thisisatest"));
//        System.out.println("------------------");
//        System.out.println(tree.search("thisisgood"));
//        OldSuffixTree tree2 = new OldSuffixTree("bananaybanana$");
//        tree2.visualize();
//        String test = "nayba";
//        System.out.println(test);
//        System.out.println(test.substring(0, 1));
//        System.out.println(test.substring(0, 2));
//        System.out.println(test.substring(0, 3));
//        System.out.println(test.substring(0, 4));
//        System.out.println(test.substring(0, 5));

        String file = TextHandler.english();
        FileReader reader = new FileReader(file);
        System.out.println("length file in bites: " + reader.getLenght());
        System.out.println("2^18=" + Math.pow(2, 18));
        String text = reader.readText(0, (int)Math.pow(2, 18));
        OldSuffixTree tree = new OldSuffixTree(TextHandler.preProcesing(text) + "$");
        System.out.println(tree.search("revolutionary"));

//        SuffixTree tree = new SuffixTree(TextHandler.preProcesing(text) + "$");
    }
}
