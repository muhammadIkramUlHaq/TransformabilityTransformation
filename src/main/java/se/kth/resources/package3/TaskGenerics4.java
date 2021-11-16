package se.kth.resources.package3;

import java.util.ArrayList;

public class TaskGenerics4<K,V> {

    private K key;

    private V value;

    private ArrayList<Long> list;
    
    private int length;

    public TaskGenerics4(K key, V value, int length) {
        this.key = key;
        this.value = value;
        this.length = length;
    }

    public void setKey(K key) { this.key = key; }
    public K getKey()   { return key; }

    public void setValue(V value) { this.value = value; }
    public V getValue()   { return value; }

    public void method2(float f) {
        System.out.println("hello world");
    }
}
