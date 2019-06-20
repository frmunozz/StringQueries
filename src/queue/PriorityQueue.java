package queue;

/**
 * A simple Priority Queue class that use a fixed max size and an integer criteria to sort his values.
 */
public class PriorityQueue {
    /**
     * the Queue Array where the data are stored
     */
    private IQObject[] queue;
    /**
     * the maximum size of the Queue Array
     */
    private int maxSize;
    /**
     * the current size of the Queue Array (the rest is trash data)
     */
    private int size;

    public PriorityQueue(int maxSize){
        this.maxSize = maxSize;
        // we initialize the queue with one additional space necessary in the add process
        this.queue = new IQObject[this.maxSize + 1];
        this.size = 0;
    }

    /**
     * add a new Queue Object to the Priority Queue
     * @param obj the new Queue Object
     */
    public void add(IQObject obj){
        int i;
        for (i = size -1; i >= 0; i--){
            if (obj.lowerThan(queue[i])){
                break;
            }
            this.displace(i);
        }
        this.set(i+1, obj);
        if (size < maxSize)
            size++;
    }

    /**
     * get a Queue Object from the Queue array
     * @param idx the index to use to get the Queue Object
     * @return
     */
    public IQObject get(int idx){
        if (idx >= size)
            return null;
        return queue[idx];
    }

    /**
     * @return current size of the Priorirt Queue
     */
    public int size(){
        return size;
    }

    /**
     * displace a Queue object in the Queue array by one index
     * @param i the index where the Queue object originally is.
     */
    protected void displace(int i){
        queue[i+1] = queue[i];
    }

    /**
     * set a Queue array index with a Queue object
     * @param i index
     * @param obj Queue object
     */
    protected void set(int i, IQObject obj){
        queue[i] = obj;
    }

    /**
     * clear the Priority Queue setting his current size to 0
     */
    public void clear(){
        size = 0;
    }
}
