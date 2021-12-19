package se.kth;

class MyClass<T> {
    T ob;/*from   w w w . j  a v a  2s .  c  o m*/
    MyClass(T o) {
        ob = o;
    }
    T getob() {
        return ob;
    }
}
class MySubclass<T, V> extends MyClass<T> {
    V ob2;

    MySubclass(T o, V o2) {
        super(o);
        ob2 = o2;
    }

    V getob2() {
        return ob2;
    }
}


public class TestGenericSubclass {
    public static void main(String args[]) {
        MySubclass<String, Integer> x = new MySubclass<String, Integer>("Value is: ", 99);
        System.out.println(x.getob());
        System.out.println(x.getob2());
    }
}