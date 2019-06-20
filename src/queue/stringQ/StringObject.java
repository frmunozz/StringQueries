package queue.stringQ;

import queue.IQObject;

public class StringObject implements IQObject {
    private String obj;
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
