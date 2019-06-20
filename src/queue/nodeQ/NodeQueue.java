package queue.nodeQ;

import queue.IQObject;
import queue.PriorityQueue;
import suffixTree.Node;

public class NodeQueue extends PriorityQueue {
    public NodeQueue(int maxSize) {
        super(maxSize);
    }

    public Node getNode(int i){
        return (Node)this.get(i).getObject();
    }

    public void add(Node node){
        add(new NodeObject(node));
    }

    public void reSort(int pos){
        IQObject cuttedChild = this.get(pos);
        int i;
        for (i = pos - 1; i >= 0; i--){
            if (cuttedChild.lowerThan(this.get(i)))
                break;
            this.displace(i);
        }
        this.set(i+1, cuttedChild);
    }
}
