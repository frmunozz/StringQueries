package suffixtree;

public class Key {
    private String str;

    public Key(){
        this.str = "";
    }

    public Key(String str){
        this.str = str;
    }

    public int length(){
        return this.str.length();
    }

    public String getStr(){
        return str;
    }

    public Key cutStr(int ini, int end){
        String cut;
        if (end == -1)
            cut = this.str.substring(ini);
        else
            cut = this.str.substring(ini, end);
        return new Key(cut);
    }

    public int preffixMatchPosition(Key other){
        int i = 0;
        int m1, m2;
        if (other.endsWith$())
            m2 = other.length() - 1;
        else
            m2 = other.length();
        if (this.endsWith$())
            m1 = this.length() - 1;
        else
            m1 = this.length();

        int m = Math.min(m1, m2);
        while (i < m){
            if (!this.charEquals(other, i))
                break;
            i++;
        }
        return Math.min(i, m);
    }

    public boolean largerThan(Key other){
        return this.length() > other.length();
    }

    public boolean charEquals(Key other, int idx){
        return this.charAt(idx) == other.charAt(idx);
    }

    public char charAt(int idx){
        return this.str.charAt(idx);
    }

    public Key copy(){
        return new Key(str);
    }

    public String toPrint(){
        return "<" + str + ">";
    }

    public boolean isEmpty(){
        return str.length() == 0;
    }

    public int compareLength(Key other){
        return Integer.compare(this.length(), other.length());
    }

    public boolean endsWith$(){
        return (this.str.charAt(this.str.length() - 1) == '$');
    }

    public void add$(){
        if (!endsWith$())
            this.str += "$";
    }
}
