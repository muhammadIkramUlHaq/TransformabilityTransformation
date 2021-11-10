package se.kth;

public class TestClassGeneric {

        public static void main (String args[]) {
            
            Pair p1 = new Pair(1, "apple");
            Pair p2 = new Pair(2, "pear");
            Pair p3 = new Pair("String", "String");
            Pair p4 = new Pair("String", "String");
            boolean same = Util.compare(p1, p2);
            boolean same1 = Util.compare(p3, p4);
            System.out.println("Value0=" + same);
            System.out.println("Value1=" + same1);

        }
}

 class Util {
    public static boolean compare(Pair p1, Pair p2) {
        return p1.getKey().equals(p2.getKey()) &&
                p1.getValue().equals(p2.getValue());
    }
}

 class Pair {

    private Object key;
    private Object value;

    public Pair(Object key,Object value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(Object key) { this.key = key; }
    public void setValue(Object value) { this.value = value; }
    public Object getKey()   { return key; }
    public Object getValue() { return value; }
}