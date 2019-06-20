package queue;

public class PriorityQueue {
    private IQObject[] queue;
    private int maxSize;
    private int size;

    public PriorityQueue(int maxSize){
        this.maxSize = maxSize;
        this.queue = new IQObject[this.maxSize + 1];
        this.size = 0;
    }

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

    public IQObject get(int idx){
        if (idx >= size)
            return null;
        return queue[idx];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void displace(int i){
        queue[i+1] = queue[i];
    }

    public void set(int i, IQObject obj){
        queue[i] = obj;
    }

    public void clear(){
        size = 0;
    }
}
