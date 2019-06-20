package pointer;

import java.util.List;

/**
 * Pointer Interface with the important methods that will be used in Suffix Tree and Node
 */
public interface IPointer {
    boolean matchChar(IPointer other, String text);
    int compareLength(IPointer other);

    /**
     * get the first char of the current prefix-suffix using the String Text
     * @param text the string that cointain the char
     * @return the char
     */
    char getFirst(String text);

    /**
     * @return the length of the current prefix-suffix
     */
    int getLength();

    /**
     * @return the position of the current prefix-suffix
     */
    int getPosition();

    /**
     * @return list with other positions where the current prefix-suffix also occurs
     */
    List<Integer> getPositions();

    /**
     * displace the current pointer in the text array to the end of the text
     * @param distance the distance to displace (forward)
     */
    void forward(int distance);

    /**
     * displace the current pointer in the text array to the begin of the text.
     * @param distance the distance to displace (backward)
     */
    void backward(int distance);

    /**
     * change the length of the current prefix-suffix
     * @param newLength the new length
     */
    void crop(int newLength);

    /**
     * give the char in the text using the pointer position and the given displacement 'i'
     * @param i  the displacement
     * @param text the text array
     * @return the resulting char
     */
    char getCharAt(int i, String text);

    /**
     * get the prefix-suffix string that is pointed in the Text
     * @param text the Text from where the string is taken.
     * @return the resulting string
     */
    String toText(String text);

    /**
     * add to additional positions array a new positions where the current prefix-suffic occurs.
     * @param pos the new positions
     */
    void addOtherPosition(int pos);

    /**
     * get the positions as a string (current and others)
     * @return the string with the positions
     */
    String positionsAsString();
}
