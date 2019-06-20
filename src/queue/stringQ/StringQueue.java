package queue.stringQ;

import queue.PriorityQueue;

/**
 * String Priority Queue that extends from Priority Queue, this implements a specific version
 * of the Priority Queue using as data a tuple of a string and an integer
 */
public class StringQueue extends PriorityQueue {
    public StringQueue(int maxSize) {
        super(maxSize);
    }

    /**
     * add a string with the corresponding count number to the Queue
     * @param str the string
     * @param count the integer
     */
    public void add(String str, int count){
        add(new StringObject(str, count));
    }

    /**
     * get a string from the Priority Queue in the position 'i'
     * @param i the position
     * @return the String
     */
    public String getString(int i){
        return (String) this.get(i).getObject();
    }
}
