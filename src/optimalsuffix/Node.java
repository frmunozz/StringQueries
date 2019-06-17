package optimalsuffix;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Pointer key;
    private int count;
    private boolean isLeaf;
    private List<Node> children;

//    for roo
    public Node(){
        this(new Pointer(), new ArrayList<>(), 0, false);
    }

//    for leaf
    public Node(Pointer key){
        this(key, new ArrayList<>(), 1, true);
    }

//    for inner
    public Node(Pointer key, List<Node> children){
        this(key, children, 0, true);
    }

    private Node(Pointer key, List<Node> children, int count, boolean isLeaf){
        this.key = key;
        this.count = count;
        this.isLeaf = isLeaf;
        this.children = children;
        for (Node child : children)
            count += child.getCount();
    }

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount(int add){
        this.count += add;
    }

    public Pointer getKey() {
        return key;
    }

    public List<Node> getChildren() {
        return children;
    }

    public int getNumberOfChildren(){
        return children.size();
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

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

    public void setKey(Pointer key){
        this.key = key;
    }

    public String toPrint(String text){
        return key.toPrint(text) + ":[" + count + "]:{" + key.positionsAsString() + "}";
    }
}
