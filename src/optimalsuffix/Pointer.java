package optimalsuffix;

import java.util.ArrayList;
import java.util.List;

public class Pointer {
    private char first;
    private int length;
    private int position;
    private List<Integer> otherPositions;

    public Pointer(char first, int length, int position){
        this.first = first;
        this.length = length;
        this.position = position;
        this.otherPositions = new ArrayList<>();
    }

    public Pointer(){
        this('_', 0, 0);
    }

    public String read(String text){
        return text.substring(this.position, this.position + this.length);
    }

    public boolean matchChar(Pointer other){
        if (this.length == 0 && this.first == '_')
            return false;
        return this.getFirst() == other.getFirst();
    }

    public int compareLength(Pointer other){
        return Integer.compare(this.getLength(), other.getLength());
    }

    public char getFirst(){
        return this.first;
    }

    public int getLength(){
        return this.length;
    }

    public int getPosition(){
        return this.position;
    }

    public void forward(int distance, String text){
        this.position += distance;
        this.length -= distance;
        if (text.length() == this.position)
            this.first = '_';
        else
            this.first = text.charAt(this.position);
    }

    public void backward(int distance, String text){
        this.position -= distance;
        this.length += distance;
        this.first = text.charAt(this.position);
    }

    public Pointer copy(){
        return new Pointer(this.first, this.length, this.position);
    }

    public char getCharAt(int i, String text){
        if (text.length() == position + i)
            return '$';
        return text.charAt(position + i);
    }

    public String toPrint(String text){
        return text.substring(position, position + length);
    }

    public void addOtherPosition(int pos){
        this.otherPositions.add(pos);
    }

    public List<Integer> getOtherPositions(){
        return this.otherPositions;
    }

    public String positionsAsString(){
        StringBuilder str = new StringBuilder();
        str.append(position);
        for (int i = 0; i < otherPositions.size(); i++){
            str.append(", ");
            str.append(otherPositions.get(i));
        }
        return str.toString();
    }

    public void crop(int newLength){
        this.length = newLength;
    }
}
