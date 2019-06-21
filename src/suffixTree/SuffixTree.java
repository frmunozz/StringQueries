package suffixTree;

import pointer.IPointer;
import pointer.Pointer;
import pointer.RootPointer;
import queue.nodeQ.NodeQueue;
import queue.stringQ.StringQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Suffix Tree class where all the logic and methods are defined, where you can find the four principal functions
 *
 * - search
 * - count
 * - locate
 * - top-K-Q
 *
 * @author Francisco Muñoz
 */
public class SuffixTree {
    /**
     * text that is going to be used in the suffix tree
     */
    private String text;
    /**
     * save the text length to simplify notation
     */
    private int textLength;
    /**
     * root Queue of nodes which will store the first nodes in the Suffix Tree
     */
    private NodeQueue root;
    /**
     * the maximum number of nodes per level, this number correspond to the addition
     * of 26 alphabet letters, 10 numbers and 5 special characters ' ' , '-' , '#' %' '?'
     */
    private static final int maxSize = 41;
    /**
     * internal variable that will store the number of nodes present in the Suffix Tree
     */
    private int counterOfNodes;
    private int leafNones;

    public SuffixTree(String text){
        this.text = text;
        this.textLength = text.length();
        this.root = new NodeQueue(maxSize);
        this.counterOfNodes = 0;
        this.leafNones = 0;
        buildTree();
    }

    /**
     * function to build the Suffix tree given the text
     */
    private void buildTree(){
        for (int i = 0; i < textLength - 1; i++){
            insertSuffix(i);
        }
    }

    /**
     * insert a new suffix to the Suffix Tree, this is given the the starting position
     * of the suffix in the Text string.
     * @param i the starting position of the suffix.
     */
    private void insertSuffix(int i){
        Node node;
        Pointer p;
        int k;
        for (k = 0; k < root.size(); k++){
            node = root.getNode(k);
            if (node.matchFirstChar(i, text)){
                p = new Pointer(textLength - i, i);
                innerInsert(node, p);
                node.getKey().addOtherPosition(i);
                root.reSort(k);
                break;
            }
        }
        if (k == root.size()){
            root.add(new Node(new RootPointer(textLength - i, i)));
            counterOfNodes++;
            leafNones++;
        }
    }

    /**
     * internal function that insert a Suffix (encapsulated in a class Pointer) to a given Node,
     * this funciton will look recursevely in the nodes until it finds a match in the characters
     * @param node The current node used to look for a match in characters
     * @param p the suffix Pointer that will be inserted
     */
    private void innerInsert(Node node, Pointer p) {
        IPointer np = node.getKey();
        int pos = splitPosition(np, p);

        if (!node.isLeaf() && pos == np.getLength() && pos < p.getLength()){
            Node child;
            p.forward(pos);
            int k;
            for (k = 0; k < node.childrenSize(); k++){
                child = node.getChild(k);
                if (child.matchFirstChar(p.getPosition(), text)){
                    innerInsert(child, p);
                    node.addCount(1);
                    node.reSort(k);
                    break;
                }
            }
            if (k == node.childrenSize()){
                p.backward(pos);
                insert(node, p, pos);
            }
        }
        else{
            insert(node, p, pos);
        }
    }

    /**
     * insert a Pointer Suffix in the given Node considering that both have the first 'pos' characters equals.
     * if the 'pos' is equal to the length of the Node key, then a new child fr the node is created, otherwise,
     * the node should be split in a way that the node content will be a father with two children, the current node
     * content and the new Pointer suffix.
     *
     * @param node the node where the Pointer suffix will be added
     * @param p the Pointer suffix to be added
     * @param pos the position of bifurcation between the two keys
     */
    private void insert(Node node, Pointer p, int pos){
        if (pos == node.getKey().getLength()){
            extend(node, p, pos);
        }
        else{
            split(node, p, pos);
        }
    }

    /**
     * extend a node adding a new child using as key the Pointer suffix 'p' taking his suffix
     * starting from position 'pos'
     *
     * @param node the node where the new child will be created
     * @param p the Pointer suffix to be used in the new child
     * @param pos the position where the sub-suffix of the Pointer suffix will start
     */
    private void extend(Node node, Pointer p, int pos){
        if (node.isLeaf()){
            node.mutate();
            leafNones--;
        }
        p.forward(pos);
        node.addChild(new Node(p));
        leafNones++;
        this.counterOfNodes++;
    }

    /**
     * split a node, creating two Node children:
     *
     * One will use as key a suffix of Pointer 'p' starting from position 'pos' and will be a leaf (no children)
     *
     * The other will use as key a suffix of Node key starting from position 'pos' and will be an internal node using
     * as children the children's Node.
     *
     * Then the node will cut his Key taking the prefix util position 'pos' and will clear his children to the add as
     * the only two children the two new Node created before.
     *
     * @param node The node to be split
     * @param p the new key to be used in a child
     * @param pos the position used to get the suffix and prefix.
     */
    private void split(Node node, Pointer p, int pos){
        Pointer p0 = new Pointer
                (node.getKey().getLength() - pos, node.getKey().getPosition() + pos);
        Pointer p1 = new Pointer
                (p.getLength() - pos, p.getPosition() + pos);
        node.getKey().crop(pos);

        Node c1 = new Node(p1, 1);
        Node c0 = new Node(p0, 1);
        if (node.isLeaf()){
            node.mutate();
            leafNones--;
        }
        else{
            c0.mutate();
            leafNones--;
            c0.addAllChildren(node.getChildren());
            node.clearChildren();
        }
        node.addChild(c1);
        leafNones++;
        node.addChild(c0);
        leafNones++;
        this.counterOfNodes++;
    }

    /**
     * Search in the Node recursive Structure for an String, this a first step method that will
     * search in every Node in 'root', calling a second step method 'innerSearch'
     * @param str the string that will be searched
     * @return The node where the match happens
     */
    private Node nodeSearch(String str){
        Node result = null;
        Node outRootNode;
        int k;
        for (k = 0; k < root.size(); k++){
            outRootNode = root.getNode(k);
            if (text.charAt(outRootNode.getKey().getPosition()) == str.charAt(0)){
                result = innerSearch(outRootNode, str, 0);
                break;
            }
        }
        return result;
    }

    /**
     * Search in the Node recursive Structure for an String, but this will just give the root Node where
     * the march of the first character happens
     * @param str the string that will be searched
     * @return the root node where the match of first character happens
     */
    private Node nodeSearchRoot(String str){
        Node result = null;
        int k;
        for (k = 0; k < root.size(); k++){
            result = root.getNode(k);
            if (text.charAt(result.getKey().getPosition()) == str.charAt(0)){
                if (innerSearch(result, str, 0) != null)
                    break;
            }
        }
        return result;
    }

    /**
     * Second step search where it looks in the Node for a suffix of the string (taking as starting point 'ini'),
     * if the match doesnt occurs in this node, it will search in his children or will return null if it fail.
     *
     * @param node the current node where is searched the string
     * @param str the searched string
     * @param ini the starting point of the suffix of the string.
     * @return
     */
    private Node innerSearch(Node node, String str, int ini){
        IPointer nk = node.getKey();
        int pos = bifurcationPoint(text.substring(nk.getPosition(), nk.getPosition() + nk.getLength()), str.substring(ini));
        if (!node.isLeaf() && pos == nk.getLength() && pos < str.length() - ini){
            Node child;
            ini += pos;
            int k;
            for (k = 0; k < node.childrenSize(); k++){
                child = node.getChild(k);
                if (text.charAt(child.getKey().getPosition()) == str.charAt(ini)){
                    return innerSearch(child, str, ini);
                }
            }
        }
        else if (pos == str.length() - ini){
            return node;
        }
        return null;
    }

    /**
     * Give the index where the characters of two strings start to be different.
     *
     * @param str1 String used in the comparison
     * @param str2 String used n the comparison
     * @return the point (index) where the two string differs.
     */
    private int bifurcationPoint(String str1, String str2){
        int i = 0;
        int m = Math.min(str1.length(), str2.length());
        while (i < m){
            if (str1.charAt(i) != str2.charAt(i))
                break;
            i++;
        }
        return i;
    }

    /**
     * give the split position of two keys, which is where the keys differs in their content (String)
     * @param p1 a Pointer key used to comparison
     * @param p2 a Pointer key used to comparison
     * @return the point(index) where the two string differs.
     */
    private int splitPosition(IPointer p1, IPointer p2){
        String s1 = text.substring(p1.getPosition(), p1.getPosition() + p1.getLength());
        String s2 = text.substring(p2.getPosition(), p2.getPosition() + p2.getLength());
        return bifurcationPoint(s1, s2);
    }

    /**
     * Search function that gives True if a String (could be as a prefix of a suffix) is found in the Suffix Tree.
     * @param str the searched string
     * @return True if the string is found, false if not.
     */
    public boolean search(String str){
        return nodeSearch(str) != null;
    }

    /**
     * Count function that gives the number of apparitions of a String (could be as a prefix of a suffix) in the
     * Text using the suffix Tree.
     * @param str the searched string
     * @return the number of apparitions
     */
    public int count(String str){
        Node result = nodeSearch(str);
        if (result == null)
            return 0;
        return result.getCount();
    }

    /**
     * Locate function that gives the locations where a string appears in the Text using the suffix tree.
     * @param str the searched string
     * @return a list with the apparition positions (ad index, starting from 0) of the string.
     */
    public List<Integer> locate(String str){
        List<Integer> out = new ArrayList<>();
        Node result = nodeSearchRoot(str);
        if (result == null)
            return out;
        for (int pos : result.getKey().getPositions()){
            if (bifurcationPoint(text.substring(pos), str) == str.length()){
                out.add(pos);
            }
        }
        Collections.sort(out);
        return out;
    }

    /**
     * top-k-q function that gives the 'k' Strings of length 'q' that appears the most in the text using the
     * Suffix Tree.
     * @param k the number of string looked for, the output could be less that this number
     * @param q the length of the string that are looked for
     * @return a list with the found Strings (best k using as parameter the number of apparitions)
     */
    public List<String> topKQ(int k, int q){
        StringQueue priQueue = new StringQueue(k);
        for (int i = 0; i < root.size(); i++){
            innerTopKQQ(root.getNode(i), q, q, priQueue);
            if (i >= k && priQueue.size() >= k)
                break;
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < priQueue.size(); i++){
            result.add(priQueue.getString(i));
        }
        return result;
    }

    /**
     * inner top-k-q search function that take the current node and check if contains the string of
     * the specific length (q), if is found, it will add the String to a Priority Queue using as priority
     * the count number. In case it fails, it will search in ALL children nodes for a string of length 'q'
     * minus node key length.
     *
     *
     * @param node the current node where topKQ search is performed
     * @param q the current length of the desired string, this number will decrease when the code go to a child.
     * @param originalQ the original length of the desired string, this number will remains constant.
     * @param pq the Priority Queue where the string is stored.
     */
    private void innerTopKQQ(Node node, int q, int originalQ, StringQueue pq){
        if (node.getKey().getLength() < q){
            if (!node.isLeaf()){
                for (int i =0; i < node.childrenSize(); i++){
                    innerTopKQQ(node.getChild(i), q - node.getKey().getLength(), originalQ, pq);
                }
            }
        }
        else if (node.getKey().getLength() == q && !node.isLeaf()){
            String str = text.substring(node.getKey().getPosition() - (originalQ - q), node.getKey().getPosition() + node.getKey().getLength());
            pq.add(str, node.getCount());
        }
        else if (node.getKey().getLength() > q){
            String str = text.substring(node.getKey().getPosition() - (originalQ - q), node.getKey().getPosition() + q);
            pq.add(str, node.getCount());
        }
    }

    /**
     * additional function that allow to visualize a suffix Tree
     */
    public void visualize() {
        if (root.size() == 0) {
            System.out.println("<empty>");
            return;
        }
        System.out.println("-┐ ");
        for (int i = 0; i < root.size() - 1; i++){
            System.out.print(" ├─");
            visualize_f(root.getNode(i), " │ ");
        }
        System.out.print(" └─");
        visualize_f(root.getNode(root.size() - 1), "   ");
    }

    /**
     * additional function that is used to visualize a suffix Tree.
     * @param node the node to visualize
     * @param pre a string with previous characters to used in the visualization.
     */
    private void visualize_f(Node node, String pre) {
        if (node.childrenSize() == 0){
            System.out.println("- " + node.toPrint(text));
            return;
        }
        System.out.println("┐ " + node.toPrint(text));
        for (int i = 0; i <  node.childrenSize() - 1; i++) {
            System.out.print(pre + "├─");
            visualize_f(node.getChild(i), pre + "│ ");
        }
        System.out.print(pre + "└─");
        visualize_f(node.getChild(node.childrenSize() - 1), pre + "  ");
    }
}
