package se.kth;

class Gen<T> {
    T ob;//ww  w .  ja v  a  2s  .  co  m

    Gen(T o) {
        ob = o;
    }
    T getob() {
        return ob;
    }
}
class Gen2 extends Gen<T> {
    Gen2(T o) {
        super(o);
    }
}

class T {

}

public class InstanceOfGenericTest {
    public static void main(String args[]) {
        Gen<Integer> iOb = new Gen<Integer>(88);
        Gen2 iOb2 = new Gen2(new T());
        System.out.println("iOb2 is instance of Gen2"+(iOb2 instanceof Gen2));
        System.out.println("iOb2 is instance of Gen"+(iOb2 instanceof Gen<?>));
    }
}