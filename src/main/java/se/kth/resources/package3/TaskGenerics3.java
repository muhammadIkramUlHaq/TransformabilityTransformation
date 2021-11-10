package se.kth.resources.package3;

public class TaskGenerics3<T extends Integer, V> {

    private T n;

    public TaskGenerics3(T n)  { this.n = n; }

    public boolean isEven() {
        return n.intValue() % 2 == 0;
    }
    
}