package suffixTree;

import pointer.IPointer;
import pointer.Pointer;
import queue.nodeQ.NodeObject;
import queue.nodeQ.NodeQueue;

/**
 * Node class that define the loginc a Suffix Tree node, includen the String position and length (Pointer key) and
 * the children. We also use a count integer to optimize the count query and a boolean 'isLeaf' to identify if the node
 * is internal or leaf.
 *
 */
public class Node {
    /**
     * Pointer where the string metadata are stored (positions and length)
     */
    private IPointer key;
    /**
     * variable that counts the number of repetitions if this prefix-suffix
     */
    private int count;
    /**
     * True if the node is leaf, flase if not
     */
    private boolean isLeaf;
    /**
     * Children array stored in a Priority Queue to get first the children prefix-suffix
     * with more repetitions (count)
     */
    private NodeQueue children;
    /**
     * Maximum number of childre, correspond to:
     * 26 from alphabet + 10 numbers + ' ', '-', '#', '?', '%' 5 special characters.
     */
    private static int maxNumberOfChildren = 41;

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

    /**
     * @return True if node is leaf, False if not
     */
    public boolean isLeaf() {
        return isLeaf;
    }

    /**
     * Mutate a node changing his definitions. There are two options:
     *
     * node was leaf: then is changed to internal node, setting count to 0.
     * node was internal: then is changed to leaf, setting count to 0 and clearing all children content.
     */
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

    /**
     * @return count number (number of aparitions of this prefix-suffix)
     */
    public int getCount() {
        return count;
    }

    /** increase the count number
     * @param add the amount that it will be increased
     */
    public void addCount(int add){
        this.count += add;
    }

    /**
     * @return Pointer key with string metaData
     */
    public IPointer getKey() {
        return key;
    }

    /**
     * clear all children from this node and set count to 0 if node is leaf.
     */
    public void clearChildren(){
        this.children.clear();
        if (!isLeaf())
            this.count = 0;
    }

    /**
     * add child to this node only if node is leaf. Also increase count by the child.count
     * @param child The new child Node to be added
     */
    public void addChild(Node child) {
        if (!isLeaf()){
            this.children.add(new NodeObject(child));
            count += child.getCount();
        }
    }

    /**
     * add all children from a Node Priority Queue only if this node is not leaf.
     * @param children The priority Queue from where the children will be added to this node.
     */
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

    /** get a children
     * @param i the position of the children in the Priority Queue
     * @return the child in the position 'i' of the Queue.
     */
    public Node getChild(int i) {
        if (this.children.size() == 0)
            return null;
        return this.children.getNode(i);
    }

    /**
     * @return Node Priority Queue with the children
     */
    public NodeQueue getChildren() {
        return children;
    }

    /**
     * @return the size of the Priority Queue with the children
     */
    public int childrenSize(){
        return children.size();
    }

    /**
     * Re sort a Node Priority Queue starting from postion 'pos
     * @param pos the position form where the re-sort method is called.
     */
    public void reSort(int pos){
        children.reSort(pos);
    }

    /**
     * match first character of the current prefix-suffix with the i-esimal character of the same Text.
     * @param i the position of the other character
     * @param text the text from where the characters are taken.
     * @return True if is the same character, else False
     */
    public boolean matchFirstChar(int i, String text){
        return key.getFirst(text) == text.charAt(i);
    }

    /**
     * auxiliar method to print a prettify version of node data
     * @param text the text from where the node takes is Pointer key.
     * @return an string with a pretty format
     */
    public String toPrint(String text){
        return "<" + key.toText(text) + ">:[" + count + "]" + key.positionsAsString();
    }
}
