package se.kth.resources.package3;

import se.kth.resources.package3.TaskGenerics1;

public class TaskGenerics2<T,M> extends TaskGenerics1<T> {

    M val;

    public TaskGenerics2(T key, M val) {
        super(key);
        this.val = val;
    }

    public final int methodGenericsTask2(final int t) {
        final int m;
        final int lm = 19;
        m = lm;
        return 1;
    }

    public TaskGenerics1<T> methodGenerics(T key, int t) {
        final int m;
        final int lm = 19;
        m = lm;
        return new TaskGenerics1<T>(key);
    }

    public M getVal() {
        return val;
    }

    public <K, V> boolean compare(TaskGenerics1<T> p1, TaskGenerics1<T> p2) {
        return true;
    }
}
