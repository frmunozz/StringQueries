package suffixTree;

import pointer.IPointer;
import pointer.Pointer;
import queue.nodeQ.NodeObject;
import queue.nodeQ.NodeQueue;

public class Node {
    private IPointer key;
    private int count;
    private boolean isLeaf;
    private NodeQueue children;
    /**
     * 26 from alphabet + 10 numbers + ' ', '-', '#', '?', '%' 5 special characters.
     */
    private static int maxNumberOfChildren = 41;

    public Node(){
        this(new Pointer(), new NodeQueue(maxNumberOfChildren), 0);
    }

    public Node(IPointer key){
        this(key,  new NodeQueue(maxNumberOfChildren), 1);
    }

    public Node(IPointer key, int count){
        this(key,  new NodeQueue(maxNumberOfChildren), count);
    }

    private Node(IPointer key, NodeQueue children, int count){
        this.key = key;
        this.count = count;
        this.isLeaf = (this.count != 0);
        this.children = children;
        for (int i = 0; i < children.size(); i++) {
            count += children.getNode(i).getCount();
        }
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void mutate(){
        if (isLeaf()){
            isLeaf = false;
            count = 0;
        }
        else {
            isLeaf = true;
            count = 1;
            children.clear();
        }
    }

    public int getCount() {
        return count;
    }

    public void addCount(int add){
        this.count += add;
    }

    public IPointer getKey() {
        return key;
    }

    public void clearChildren(){
        this.children.clear();
        if (!isLeaf())
            this.count = 0;
    }

    public void addChild(Node child) {
        if (!isLeaf()){
            this.children.add(new NodeObject(child));
            count += child.getCount();
        }
    }

    public void addAllChildren(NodeQueue children){
        if (!isLeaf()){
            for (int i = 0; i < children.size(); i++){
                this.children.add(children.get(i));
                count += children.getNode(i).getCount();
                for (int k : children.getNode(i).getKey().getPositions())
                    key.addOtherPosition(k);
            }
        }
    }

    public Node getChild(int i) {
        if (this.children.size() == 0)
            return null;
        return this.children.getNode(i);
    }

    public NodeQueue getChildren() {
        return children;
    }

    public int childrenSize(){
        return children.size();
    }

    public void reSort(int pos){
        children.reSort(pos);
    }

    public boolean matchFirstChar(int i, String text){
        return key.getFirst(text) == text.charAt(i);
    }

    public String toPrint(String text){
        return "<" + key.toText(text) + ">:[" + count + "]" + key.positionsAsString();
    }

    public void addOtherPosition(int pos){
        key.addOtherPosition(pos);
    }
}
