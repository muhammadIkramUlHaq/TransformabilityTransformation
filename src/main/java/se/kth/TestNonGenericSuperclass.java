package se.kth;

class MyClass1 {//from   ww w  . j  ava  2  s . c o m
    int num;

    MyClass1(int i) {
        num = i;
    }

    int getnum() {
        return num;
    }
}
class MySubclass1<T> extends MyClass1 {
    T ob;
    MySubclass1(T o, int i) {
        super(i);
        ob = o;
    }
    T getob() {
        return ob;
    }
}
public class TestNonGenericSuperclass {
    public static void main(String args[]) {
        MySubclass1<String> w = new MySubclass1<String>("Hello", 4);
        System.out.print(w.getob() + " ");
        System.out.println(w.getnum());
    }
}