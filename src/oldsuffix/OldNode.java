package oldsuffix;

import java.util.ArrayList;
import java.util.List;

public class OldNode {
    private String sub;
    private List<Integer> childen;
    private int suffixCounter;

    public OldNode(){
        sub = "";
        childen = new ArrayList<>();
        suffixCounter = 0;
    }

    public OldNode(String sub){
        this.sub = sub;
        childen = new ArrayList<>();
        suffixCounter = 1;
    }

    public void increaseSuffixCounter(){
        suffixCounter++;
    }

    public int getSuffixCounter(){
        return suffixCounter;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public List<Integer> getChilden() {
        return childen;
    }

    public void setChilden(List<Integer> childen) {
        this.childen = childen;
    }

    public void addChild(int c) {
        this.childen.add(c);
    }

    public void setChild(int i, int c) {
        this.childen.set(i, c);
    }

    public int getChild(int i) {
        return this.childen.get(i);
    }

    public boolean isLeaf() {
        return this.childen.isEmpty();
    }

    public boolean isSubstringLargerThan(String str){
        return this.sub.length() > str.length();
    }

    public boolean matchPrefix(String str) {
        if (!isSubstringLargerThan(str))
            return false;
        else {
            return this.sub.substring(0, str.length()).equals(str);
        }
    }

    public boolean matchString(String str) {
        if (!isSubstringLargerThan(str))
            return str.substring(0, this.sub.length()).equals(this.sub);
        else
            return this.sub.substring(0, str.length()).equals(str);
    }

    public int numberOfChilder() {
        return this.childen.size();
    }
}
