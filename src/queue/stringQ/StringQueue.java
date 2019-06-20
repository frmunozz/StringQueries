package queue.stringQ;

import queue.PriorityQueue;

public class StringQueue extends PriorityQueue {
    public StringQueue(int maxSize) {
        super(maxSize);
    }

    public void add(String str, int count){
        add(new StringObject(str, count));
    }

    public String getString(int i){
        return (String) this.get(i).getObject();
    }
}
