package queue.nodeQ;

import queue.IQObject;
import suffixTree.Node;

/**
 * Node Queue Object that implemente Queue Object Interface, used for the Node Queue class
 */
public class NodeObject implements IQObject {
    private Node obj;

    public NodeObject(Node obj) {
        this.obj = obj;
    }

    @Override
    public int integerCriteria() {
        return obj.getCount();
    }

    @Override
    public boolean lowerThan(IQObject obj) {
        return this.integerCriteria() < obj.integerCriteria();
    }

    @Override
    public Object getObject() {
        return obj;
    }
}
