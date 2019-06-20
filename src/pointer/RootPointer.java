package pointer;

import java.util.ArrayList;
import java.util.List;

/**
 * Root Pointer class that extends Pointer class, this class implements a Pointer in a Root node
 * since it hold different information that an internal node of the suffix tree structure.
 */
public class RootPointer extends Pointer{
    /**
     * List with the other positions where the current prefix-suffix occurs.
     */
    private List<Integer> otherPositions;

    public RootPointer(int length, int position){
        super(length, position);
        otherPositions = new ArrayList<>();
    }

    @Override
    public void addOtherPosition(int pos) {
        this.otherPositions.add(pos);
    }

    @Override
    public List<Integer> getPositions() {
        List<Integer> r = new ArrayList<>();
        r.add(this.getPosition());
        r.addAll(this.otherPositions);
        return r;
    }

    @Override
    public String positionsAsString(){
        StringBuilder str = new StringBuilder();
        str.append("->{");
        str.append(this.getPosition());
        for (int i = 0; i < otherPositions.size(); i++){
            str.append(", ");
            str.append(otherPositions.get(i));
        }
        str.append("}");
        return str.toString();
    }
}
