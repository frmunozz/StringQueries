package suffixtree;

import java.util.ArrayList;

public class SuffixTree {
    private Node root;

    public SuffixTree(String text){
        root = new Node();
        for (int i = 0; i < text.length(); i++) {
            Key key = new Key(text.substring(i));
            addSuffix(key);
//            System.out.println("adding (" + i + "): <" + key.getStr() + ">");
//            System.out.println("===============");
//            visualize();
//            System.out.println("===============");
        }
    }

    private void addSuffix(Key key) {
        String foo = "revolutionary";
        if (key.getStr().length() >= foo.length() && key.getStr().substring(0, foo.length()).equals(foo))
            System.out.println("debug");
//        System.out.println("adding <" + key.getStr() + ">");
        for (Node node : root.getChildren()) {
//            int pos = node.prefixMatchPosition(str.substring(0, str.length()-1));
            int pos = node.getKey().preffixMatchPosition(key);
            if (pos != 0){
//                System.out.println(":: descending to node with key <" + node.getKey() + ">");
                root.incrementSuffixCounter();
                addSuffixNested(node, key, pos);
                return;
            }
        }
//        System.out.println(":: no match found, creating new node with key: <" + key.getStr() + ">");
        root.addChild(new Node(key));
    }

    private void addSuffixNested(Node node, Key key, int pos) {
        int comp = node.compareLength(key);
        if (comp == 1){
//            System.out.println("node <" + node.getKey() + ">;[" + node.getCount() + "] is larger than str <" + key.getStr() + "> so we split");
            split(node, key, pos);
        }
        else{
            if (node.isLeaf()){
//                System.out.println("node <" + node.getKey() + ">:[" + node.getCount() + "] is leaf and smaller than str <" + key.getStr() + ">, so we extend");
                extend(node, key, pos);
            }
            else{
                int pos2 = key.preffixMatchPosition(node.getKey());
//                if (str.substring(0, node.getKey().length()).equals(node.getKey())){
                if (pos2 != 0){
                    //                bajo al hijo que coincida por prefijo
//                    String s = str.substring(pos);
                    Key k = key.cutStr(pos, -1);
//                    System.out.println("node <" + node.getKey() + ">:[" + node.getCount() + "] is not leaf and smaller than str <" + key.getStr() + "> so we descend to child");
                    for (Node n : node.getChildren()){
//                        int p = n.prefixMatchPosition(s.substring(0, s.length()-1));
                        int p = n.getKey().preffixMatchPosition(k);
                        if (p != 0) {
//                            System.out.println(":: descending to child <" + n.getKey() + ">:[" + n.getCount() + "] for sub.str <" + k.getStr() + ">");
                            node.incrementSuffixCounter();
                            addSuffixNested(n, k, p);
                            return;
                        }
                    }
//                    node.addChild(new Node(str.substring(node.getKey().length())));
                    if (pos2 == node.getKey().length()){
                        node.addChild(new Node(key.cutStr(node.getKey().length(), -1)));
                    }
                    else{
                        split(node, key, pos2);
                    }
                }
                else{
//                    debo separar los nodos
                    split(node, key, pos);
                }
            }
        }
    }


    private void split(Node node, Key key, int pos){
        Key k = node.getKey().copy();
        node.setKey(k.cutStr(0, pos));
        Node c1 = new Node(key.cutStr(pos, -1));
        c1.getKey().add$();
        Node c2 = new Node(k.cutStr(pos, -1));
//        if (node.isLeaf()){
//            node.mutate();
////            node.incrementSuffixCounter();
//        }
//        else{
//            c2.mutate();
//            for (Node child : node.getChildren()){
//                c2.addChild(child);
//            }
////            c2.setCount(node.getCount());
//            node.clearChildren();
////            node.setCount(c2.getCount());
//        }
//        node.addChild(c1);
//        node.addChild(c2);
    }

    private void extend(Node node, Key key, int pos){
        if (node.isLeaf()){
            node.mutate();
//            node.incrementSuffixCounter();
        }
        node.addChild(new Node(key.cutStr(pos, -1)));
    }

    public void print(){
        innerPrint(root, "0");
    }
    public void innerPrint(Node node, String id){
        System.out.println("OldNode " + id + ", key: " + node.toPrint());
//        System.out.println("");
        if (node.isLeaf())
            return;
        for (int i = 0; i < node.numberOfChildren(); i++){
            innerPrint(node.getChild(i), id + "." + i);
        }
    }

    public boolean search(String str){
        return innerSearch(root, new Key(str));
    }

    public boolean innerSearch(Node node, Key key){
//        System.out.println("searching "+ key.toPrint() + " in node " + node.toPrint());
        if (key.isEmpty()){
//            System.out.println(":: str " + key.toPrint() + " is empty");
            return true;
        }
        if (node.isLeaf()){
//            System.out.println(":: node " + node.toPrint() + " is leaf, so we ask directly");
            return node.getKey().getStr().equals(key.getStr());
        }
        for (Node child : node.getChildren()){
//            if (child.isKeyLargerThan(str)) {
            if (child.getKey().largerThan(key)) {
//                if (child.getKey().substring(0, str.length()).equals(str)){
                if (child.getKey().getStr().substring(0, key.getStr().length()).equals(key.getStr())){
//                    System.out.println(":: child " + child.toPrint() + " contains str " + key.toPrint());
                    return true;
                }
            }
            else {
//                if (str.substring(0, child.getKey().length()).equals(child.getKey())){
                if (key.getStr().substring(0, child.getKey().getStr().length()).equals(child.getKey().getStr())){
//                    System.out.println(":: descending to child " + child.toPrint());
//                    return innerSearch(child, str.substring(child.getKey().length()));
                    return innerSearch(child, key.cutStr(child.getKey().getStr().length(), -1));
                }
            }
        }
        return false;
    }

    public int count(String str){
        return innerCount(root, new Key(str));
    }

    public int innerCount(Node node, Key key){
//        System.out.println("searching " + key.toPrint() + " in node " + node.toPrint());
        if (key.isEmpty()){
//            System.out.println("str is empty so we found match in this node with [" + node.getCount() + "]");
            return node.getCount();
        }

        if (node.isLeaf()) {
//            System.out.println(":: node is leaf");
//            if (node.getKey().equals(str)){
            if (node.getKey().getStr().equals(key.getStr())){
//                System.out.println(":::: we found te string with number [" + node.getCount()+ "]");
                return node.getCount();
            }
            else
//                System.out.println(":::: no matching found!");
                return 0;
        }
//        System.out.println(":: node is internal, we look in  the children");
        for (Node child : node.getChildren()){
//            System.out.println(":::: checking node " + child.toPrint() + " for " + key.toPrint());
//            if (child.isKeyLargerThan(str)) {
            if (child.getKey().largerThan(key)) {
//                System.out.println(":::: node is larger tan str");
//                if (child.getKey().substring(0, str.length()).equals(str)){
                if (child.getKey().getStr().substring(0, key.getStr().length()).equals(key.getStr())){
//                    System.out.println(":::: node contains the str so we get [" + child.getCount() + "]");
                    return child.getCount();
                }
            }
            else{
//                System.out.println(":::: node is smaller or equal to str");
//                if (str.substring(0, child.getKey().length()).equals(child.getKey())){
                if (key.getStr().substring(0, child.getKey().getStr().length()).equals(child.getKey().getStr())){
//                    System.out.println(":::: node " + child.toPrint() + " is a prefix of " + key.toPrint() + " so we descend");
//                    return innerCount(child, str.substring(child.getKey().length()));
                    return innerCount(child, key.cutStr(child.getKey().getStr().length(), -1));
                }
            }
        }
//        System.out.println(":: no matching found in internal node!");
        return 0;
    }

    public void visualize() {
        if (root.numberOfChildren() == 0) {
            System.out.println("<empty>");
            return;
        }
        visualize_f(root, "");
    }

    private void visualize_f(Node node, String pre) {
        if (node.numberOfChildren() == 0){
            System.out.println("- " + node.toPrint());
            return;
        }
        System.out.println("┐ " + node.toPrint());
        for (int i = 0; i <  node.numberOfChildren() - 1; i++) {
            System.out.print(pre + "├─");
            visualize_f(node.getChild(i), pre + "│ ");
        }
        System.out.print(pre + "└─");
        visualize_f(node.getChild(node.numberOfChildren() - 1), pre + "  ");
    }


    public static void main(String[] args) {
        SuffixTree tree = new SuffixTree("bananaybananaybananaybababana$");
        System.out.println("---------------------");
//        OldSuffixTree tree2 = new OldSuffixTree("bananaybananaybananaybababana$");
//        System.out.println("-------------------------");
//        tree.print();
//        int c = 0;
//        String s = "bananaybananaybananaybababana$";
//        for (int i = 0; i < s.length()-1; i++){
//            if (s.substring(i, i+2).equals("na"))
//                c++;
//        }
//        System.out.println(c);

        System.out.println("------------------------------------------------");
        tree.visualize();
        System.out.println(tree.count("banana"));
        System.out.println(tree.count("na"));
        System.out.println(tree.count("a"));
        System.out.println(tree.count("bananaybananaybananaybababana"));
        System.out.println(tree.count("na&"));
//        SuffixTree tree3 = new SuffixTree("bananaybanana$");
//        System.out.println(tree3.count("banana"));

//        System.out.println("-------------------------");
//        tree2.visualize();
//        System.out.println(tree.search("banana$"));
//        System.out.println("-------------------------");
//        System.out.println(tree.search("anana$"));
//        System.out.println("-------------------------");
//        System.out.println(tree.search("nana$"));
//        System.out.println("-------------------------");
//        System.out.println(tree.search("na"));
//        System.out.println("-------------------------");
//        System.out.println(tree.search("na$"));
//        System.out.println("-------------------------");
//        System.out.println(tree.search("thisisgood$"));
//        System.out.println("-------------------------");
//        System.out.println(tree.search("a"));
//        System.out.println("-------------------------");
//        System.out.println(tree.search("$a"));
    }
}
