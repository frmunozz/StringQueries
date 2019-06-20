package pointer;

import java.util.ArrayList;
import java.util.List;

public class RootPointer extends Pointer{
    private List<Integer> otherPositions;

    public RootPointer(int length, int position){
        super(length, position);
        otherPositions = new ArrayList<>();
    }

    public RootPointer(){
        this(0, 0);
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
