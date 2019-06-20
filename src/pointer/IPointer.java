package pointer;

import java.util.List;

public interface IPointer {
    boolean matchChar(IPointer other, String text);
    int compareLength(IPointer other);
    char getFirst(String text);
    int getLength();
    int getPosition();
    List<Integer> getPositions();
    void forward(int distance);
    void backward(int distance);
    void crop(int newLength);
    char getCharAt(int i, String text);
    String toText(String text);
    void addOtherPosition(int pos);
    String positionsAsString();
}
