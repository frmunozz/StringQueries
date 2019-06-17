package suffixtree;

import instrumentation.InstrumentationAgent;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Key key;
    private List<Node> children;
    private int count;
    private boolean isLeaf;

//    for root
    public Node() {
        this.key = new Key();
        children = new ArrayList<>();
        count = 0;
        isLeaf = false;
    }

//    for leaf
    public Node(Key key){
        this.key = key;
        children = new ArrayList<>();
        count = 1;
        isLeaf = true;
    }

//    for internal node
    public Node(Key key, List<Node> children){
        this.key = key;
        count = 0;
        this.children = children;
        for (Node child : children)
            count += child.getCount();
        isLeaf = false;
    }

    public void incrementSuffixCounter() {
        if (!isLeaf)
            count++;
    }

    public void setCount(int count){
        if (!isLeaf)
            this.count = count;
    }


    public int getCount() {
        return count;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public List<Node> getChildren(){
        return this.children;
    }

//     when we want to erase the children of an internal node
    public void clearChildren(){
        this.children = new ArrayList<>();
        this.count = 0;
    }

    public void addChild(Node child) {
        if (!isLeaf){
            this.children.add(child);
            count += child.getCount();
        }
    }

    public Node getChild(int i) {
        if (this.children.size() == 0)
            return null;
       return this.children.get(i);
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public boolean isKeyLargerThan(String str){
        if (!isLeaf())
            return this.key.length() > str.length() - 1;
        return this.key.length() > str.length();
    }

    public int numberOfChildren() {
        return this.children.size();
    }

    public int prefixMatchPosition(Key key){
//        int i = 0;
//        int m = Math.min(str.length(), key.length());
//        while (i < m){
//            if (key.charAt(i) != str.charAt(i))
//                break;
//            i++;
//        }
//        return Math.min(i, m);
        return this.key.preffixMatchPosition(key);
    }

    public int compareLength(Key key){
        return this.key.compareLength(key);
    }

    public String toPrint(){
        return "<" + key.getStr() + ">:[" + count + "]";
    }

//    transform a leaf node to an internal node
    public void mutate(){
        if (isLeaf){
            isLeaf = false;
            count = 0;
        }
        else {
            isLeaf = true;
            count = 1;
            children = new ArrayList<>();
        }
    }
    public static void main(String [] args) {
        System.out.println(InstrumentationAgent.getObjectSize(new Key()));
    }
}
