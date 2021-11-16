package se.kth.resources.package3;

public class TaskGenerics1<T> {

    private T key;

    public TaskGenerics1(T key) {
        this.key = key;
    }

    public void setKey(T key) { this.key = key; }
    public T getKey()   { return key; }

    public int method2(float f) {
        System.out.println("hello world");
        return 0;
    }
}
