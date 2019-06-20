package queue.stringQ;

import queue.IQObject;

/**
 * String Object that implements the Queue Object Interface and is used in the String Priorirty Queue
 */
public class StringObject implements IQObject {
    /**
     * The string stored in the Queue
     */
    private String obj;
    /**
     * The count number stored in the Queue used as priority (higher the better)
     */
    private int count;

    public StringObject(String str, int count){
        obj = str;
        this.count = count;
    }

    @Override
    public int integerCriteria() {
        return count;
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
