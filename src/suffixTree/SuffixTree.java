package suffixTree;

import pointer.IPointer;
import pointer.Pointer;
import pointer.RootPointer;
import queue.nodeQ.NodeQueue;
import queue.stringQ.StringObject;
import queue.stringQ.StringQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SuffixTree {
    private String text;
    private int textLength;
    private NodeQueue root;
    private static final int maxSize = 41;
    private int foo;

    public SuffixTree(String text){
        this.text = text;
        this.textLength = text.length();
        this.root = new NodeQueue(maxSize);
        buildTree();
    }

    private void buildTree(){
        for (int i = 0; i < textLength - 1; i++){
            if (i == 556007)
                System.out.println("debug");
//            System.out.println("adding: " + i + ", <" + text.substring(i) + ">");
            foo = i;
            insertSuffix(i);
//            System.out.println("===============");
//            visualize();
//            System.out.println("===============");
        }
    }

    private void insertSuffix(int i){
        Node node;
        Pointer p;
        int k;
        for (k = 0; k < root.size(); k++){
            node = root.getNode(k);
            if (i == 556007){
//                String dummy1 = text.substring(node.getKey().getPosition(), node.getKey().getPosition() + 5);
//                String dummy2 = text.substring(i, i + 5);
                StringBuilder sb = new StringBuilder();
                for (int w = 0; w < node.childrenSize(); w++){
                    sb.append("<");
                    sb.append(text.charAt(node.getChild(w).getKey().getPosition()));
                    sb.append(">, ");
                }
                String dummy3 = sb.toString();
                foo = i;
            }
            if (node.matchFirstChar(i, text)){
                p = new Pointer(textLength - i, i);
                innerInsert(node, p);
//                node.addCount(1);
                node.getKey().addOtherPosition(i);
                root.reSort(k);
                break;
            }
        }
        if (k == root.size()){
            root.add(new Node(new RootPointer(textLength - i, i)));
        }
    }

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

    private void insert(Node node, Pointer p, int pos){
        if (pos == node.getKey().getLength()){
            extend(node, p, pos);
        }
        else{
            split(node, p, pos);
        }
    }

    private void extend(Node node, Pointer p, int pos){
        if (node.isLeaf()){
            node.mutate();
        }
        p.forward(pos);
        if (node.childrenSize() == 41){
//            visualize_f(node, ":: ");
            System.out.println("rebased node!!!");
        }
        node.addChild(new Node(p));
    }

    private void split(Node node, Pointer p, int pos){
        Pointer p0 = new Pointer
                (node.getKey().getLength() - pos, node.getKey().getPosition() + pos);
        Pointer p1 = new Pointer
                (p.getLength() - pos, p.getPosition() + pos);
        node.getKey().crop(pos);

        Node c1 = new Node(p1, 1);
        Node c0 = new Node(p0, 1);
        if (node.isLeaf())
            node.mutate();
        else{
            c0.mutate();
            c0.addAllChildren(node.getChildren());
            node.clearChildren();
        }
        node.addChild(c1);
        if (node.childrenSize() == 41)
            System.out.println("rebased node!!!");
        node.addChild(c0);
    }

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

    private int splitPosition(IPointer p1, IPointer p2){
        int i = 0;
        int m = Math.min(p1.getLength(), p2.getLength());
        while (i < m){
            if (text.charAt(p1.getPosition() + i) != text.charAt(p2.getPosition() + i))
                break;
            i++;
        }
        return i;
    }

    public boolean search(String str){
        return nodeSearch(str) != null;
    }

    public int count(String str){
        Node result = nodeSearch(str);
        if (result == null)
            return 0;
        return result.getCount();
    }

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
//        out.addAll(result.getKey().getPositions());
        Collections.sort(out);
        return out;
    }

    public List<String> topKQ(int k, int q){
        StringQueue priQueue = new StringQueue(k);
        for (int i = 0; i < root.size(); i++){
            innerTopKQQ(root.getNode(i), q, q, priQueue);
            if (priQueue.size() >= k)
                break;
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < priQueue.size(); i++){
            result.add(priQueue.getString(i));
        }
        return result;
    }

    private void innerTopKQQ(Node node, int q, int originalQ, StringQueue pq){
//        System.out.println("searching topKQ(q="+q+", origQ="+originalQ+") in node: " + node.toPrint(text));
        if (node.getKey().getLength() < q){
//            System.out.println(":: node is shortest");
//            tengo que bajar
//            pero si estoy en una hoja, retorno una lista vacia
            if (!node.isLeaf()){
//                System.out.println(":: node is internal, so we descend");
//                bajo a cada hijo a realizar la misma busqueda
                for (int i =0; i < node.childrenSize(); i++){
                    innerTopKQQ(node.getChild(i), q - node.getKey().getLength(), originalQ, pq);
                }
//                System.out.println(":: done searching children for node: " + node.toPrint(text));
            }
//            else
//                System.out.println(":: node is leaf so we end");
        }
//        si el largo es igual y no soy hoja, entonces contengo el string del largo especificado
//        NOTA: EXCLUIMOS EL $
        else if (node.getKey().getLength() == q && !node.isLeaf()){
            String str = text.substring(node.getKey().getPosition() - (originalQ - q), node.getKey().getPosition() + node.getKey().getLength());
//            String sb = ":: node is equal length, we get <" +
//                    str +
//                    ">:[" +
//                    node.getCount() +
//                    "]";
//            System.out.println(sb);
            pq.add(str, node.getCount());
        }
//        en el largo es igual y soy hoja, entonces fallo y entrego la lista vacia
//        si el largo es mayor, entonces contengo el string, pero debo cortarlo al largo deseado
        else if (node.getKey().getLength() > q){
            String str = text.substring(node.getKey().getPosition() - (originalQ - q), node.getKey().getPosition() + q);
//            StringBuilder sb = new StringBuilder();
//            sb.append(":: node is larger, we get <");
//            sb.append(str);
//            sb.append(">:[");
//            sb.append(node.getCount());
//            sb.append("]");
//            System.out.println(sb.toString());
            pq.add(str, node.getCount());
        }
//        System.out.println(":: we end");
    }

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

    public static void main(String[] args) {
        SuffixTree tree = new SuffixTree("banana y bababana$");
        tree.visualize();
        System.out.println(tree.count("a"));
        System.out.println(tree.count("na"));
        System.out.println(tree.count("ba"));
        System.out.println(tree.locate("na"));
        System.out.println(tree.locate("y bababana$"));
        System.out.println(tree.topKQ(4, 3));
    }
}
