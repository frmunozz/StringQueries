package queue.nodeQ;

import queue.IQObject;
import queue.PriorityQueue;
import suffixTree.Node;

/**
 * Node Queue class that extends a Priority Queue, changing the general 'Object' to a 'Node' object
 */
public class NodeQueue extends PriorityQueue {
    public NodeQueue(int maxSize) {
        super(maxSize);
    }

    /**
     * get the node from the Queue in the given position
     * @param i the position
     * @return the node
     */
    public Node getNode(int i){
        return (Node)this.get(i).getObject();
    }

    /**
     * add a new node to the Node Priority Queue
     * @param node the new node
     */
    public void add(Node node){
        add(new NodeObject(node));
    }

    /**
     * re-sort a Node Priority Queue starting from the given position
     * @param pos the position
     */
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
