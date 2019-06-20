package pointer;

import java.util.ArrayList;
import java.util.List;

public class Pointer implements IPointer {
    private int length;
    private int position;

    public Pointer(int length, int position){
        this.length = length;
        this.position = position;
    }

    public Pointer(){
        this(0, 0);
    }


    @Override
    public boolean matchChar(IPointer other, String text) {
        return this.getFirst(text) == other.getFirst(text);
    }

    @Override
    public int compareLength(IPointer other) {
        return Integer.compare(this.getLength(), other.getLength());
    }

    @Override
    public char getFirst(String text) {
        return this.getCharAt(0, text);
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public List<Integer> getPositions() {
        return new ArrayList<>();
    }

    @Override
    public void forward(int distance) {
        this.position += distance;
        this.length -= distance;
    }

    @Override
    public void backward(int distance) {
        this.position -= distance;
        this.length += distance;
    }

    @Override
    public void crop(int newLength) {
        this.length = newLength;
    }

    @Override
    public char getCharAt(int i, String text) {
        if (text.length() == position + i)
            return '$';
        return text.charAt(position + i);
    }

    @Override
    public String toText(String text) {
        if (length < 0){
            System.out.println("error");
        }
        return text.substring(position, position + length);
    }

    @Override
    public void addOtherPosition(int pos) {
//        do nothing
    }

    @Override
    public String positionsAsString() {
        return "->{" + this.getPosition() + "}";
    }
}
