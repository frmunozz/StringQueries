package optimalsuffix;

public class SuffixTree {
    private Node root;
    private String text;

    public SuffixTree(String text){
        this.text = text;
        this.root = new Node();
        int l = text.length();
        for (int i = 0; i < l; i++) {
//            if (i == 19)
//                System.out.println("debug");
            Pointer key = new Pointer(text.charAt(i), l-i, i);
            addSuffix(key);
//            System.out.println("adding (" + i + "): <" + text.substring(i) + ">");
//            System.out.println("===============");
//            visualize();
//            System.out.println("===============");
        }
    }

    private void addSuffix(Pointer key){
        boolean done = false;
        int i = 0;
        while (!done && i < root.getNumberOfChildren()){
            Node child = root.getChild(i);
            done = addSuffixNested(child, key);
            i++;
        }
        if (!done){
            root.getKey().addOtherPosition(key.getPosition());
            add(root, key);
        }
    }

    private boolean addSuffixNested(Node node, Pointer key){
        boolean done = false;
        if (node.getKey().matchChar(key)){
            int comp = node.getKey().compareLength(key);
            if (comp == 1 || (comp == -1 && node.isLeaf())){
                node.getKey().addOtherPosition(key.getPosition());
                add(node, key);
                done = true;
            }
            else if (!node.isLeaf() && comp == -1){
                key.forward(node.getKey().getLength(), text);
                for (Node child : node.getChildren()){
                    if (child.getKey().matchChar(key)){
                        done = addSuffixNested(child, key);
                        if (done){
                            node.addCount(1);
                            key.backward(node.getKey().getLength(), text);
                            node.getKey().addOtherPosition(key.getPosition());
                            break;
                        }
                    }
                }
                if (!done){
//                    node.addCount(1);
//                    node.getKey().addOtherPosition(key.getPosition());
                    key.backward(node.getKey().getLength(), text);
//                        no pude agregarlo en los hijos existentes asique creo nuevo hijo
                    node.getKey().addOtherPosition(key.getPosition());
                    add(node, key);
                    done = true;
                }
            }

//            int comp = node.getKey().compareLength(key);
//
//
//            if (comp == 1){
////                el nodo es mayor y debo partirlo en dos
//                add(node, key);
//                done = true;
//            }
//            else if (comp == -1){ // comp == -1
////                el nodo es mejor y debo seguir bajando (si es interno) o
////                agregar hijo (si es hoja)
//                if (node.isLeaf()){
////                    agrego hoja
//                    add(node, key);
//                    done = true;
//                }
//                else{
////                    bajo a algun hijo
//                    key.forward(node.getKey().getLength(), text);
//                    for (Node child : node.getChildren()){
//                        if (child.getKey().matchChar(key)){
//                            done = addSuffixNested(child, key);
//                            if (done){
//                                break;
//                            }
//                        }
//                    }
//                    if (!done){
//                        key.backward(node.getKey().getLength(), text);
////                        no pude agregarlo en los hijos existentes asique creo nuevo hijo
//                        add(node, key);
//                        done = true;
//                    }
//                }
//            }
        }
        return done;
    }

    public void add(Node node, Pointer key){
        Pointer nodeKey = node.getKey().copy();
        int bif = getBifurcation(nodeKey, key, text, text);
        if (bif < nodeKey.getLength()){
            split(node, nodeKey, key, bif);
        }
        else{
            extend(node, key, bif);
        }
    }

    public int getBifurcation(Pointer nodeKey, Pointer key, String textNode, String textKey){
        int bifurcation = 0;
        int m = Math.min(nodeKey.getLength(), key.getLength());
        while (bifurcation < m && textNode.charAt(nodeKey.getPosition() + bifurcation) == textKey.charAt(key.getPosition() + bifurcation)) {
            bifurcation++;
        }
        return bifurcation;
    }

    public void split(Node node, Pointer nodeKey, Pointer key, int bif){
//        node.setKey(new Pointer(nodeKey.getFirst(), bif, nodeKey.getPosition()));
        node.getKey().crop(bif);
        Node c1 = new Node(new Pointer(key.getCharAt(bif, text), key.getLength() - bif, key.getPosition() + bif));
        Node c2 = new Node(new Pointer(nodeKey.getCharAt(bif, text), nodeKey.getLength() - bif, nodeKey.getPosition() + bif));

        if (node.isLeaf())
            node.mutate();
        else{
            c2.mutate();
            for (Node child : node.getChildren()){
                c2.addChild(child);
            }
            node.clearChildren();
        }
        node.addChild(c1);
        node.addChild(c2);
    }

    public void extend(Node node, Pointer key, int bif){
        if (node.isLeaf())
            node.mutate();
        Pointer childKey = key.copy();
        childKey.forward(bif, text);
        node.addChild(new Node(childKey));
//        node.getKey().addOtherPosition(key.getPosition());
    }

    public boolean search(String str){
        for (Node child : root.getChildren()){
            Pointer key = new Pointer(str.charAt(0), str.length(), 0);
            if (innerSearch(child, key, str))
                return true;
        }
        return false;
    }

    public boolean innerSearch(Node node, Pointer key, String str){
        int comp = node.getKey().compareLength(key);
        if (node.getKey().matchChar(key)){
            Pointer nodeKey = node.getKey().copy();
            if (comp == 1){
//               el nodo actual es mayor que el string buscado asique este nodo debe contenerlo
                int bif = getBifurcation(nodeKey, key, text, str);
                return bif + key.getPosition() == str.length();
            }
            else if (comp == 0 && !node.isLeaf()){
//                el nodo actual es igual al string buscado. Siempre que no sea un nodo hoja, entonces
//                este nodo debe contener al string
                int bif = getBifurcation(nodeKey, key, text, str);
                return bif == nodeKey.getLength();
            }
            else{
//                el nodo actual es menor en largo al string, o igual en largo pero siendo nodo hoja,
//                entonces bajamos a un nodo hijo para continuar la bsuqueda.
                if (!node.isLeaf()){
//                    if (key.getLength() == node.getKey().getLength()){
//                        itn bif
//                    }
                    key.forward(node.getKey().getLength(), str);
                    for (Node child : node.getChildren()){
                        if (innerSearch(child, key, str)){
                            key.backward(1, str);
                            return true;
                        }
                    }
                }
                else{
                    int bif = getBifurcation(nodeKey, key, text, str);
                    return bif == nodeKey.getLength();
                }
            }
        }
        return false;
    }

    public void visualize() {
        if (root.getNumberOfChildren() == 0) {
            System.out.println("<empty>");
            return;
        }
        visualize_f(root, "");
    }

    private void visualize_f(Node node, String pre) {
        if (node.getNumberOfChildren() == 0){
            System.out.println("- " + node.toPrint(text));
            return;
        }
        System.out.println("┐ " + node.toPrint(text));
        for (int i = 0; i <  node.getNumberOfChildren() - 1; i++) {
            System.out.print(pre + "├─");
            visualize_f(node.getChild(i), pre + "│ ");
        }
        System.out.print(pre + "└─");
        visualize_f(node.getChild(node.getNumberOfChildren() - 1), pre + "  ");
    }

    public static void main(String[] args) {
//        SuffixTree tree = new SuffixTree("bananaybananaybananaybababana$");
        SuffixTree tree = new SuffixTree("banana$");
        tree.visualize();
        System.out.println(tree.search("na"));
        System.out.println(tree.search("abana"));
    }
}
