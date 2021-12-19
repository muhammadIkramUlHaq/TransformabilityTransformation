package se.kth.resources.package3;

public class TaskGenerics2<T,M> extends TaskGenerics1<T> implements TestGenericInterface1<T> {

    M[] val;

    public TaskGenerics2(T key, M[] val) {
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
        return new TaskGenerics1<>(key);
    }

    public TaskGenerics1<Integer>[] methodGenericsArrayReturnTest(T key, int t) {
        final int m;
        final int lm = 19;
        m = lm;
        TaskGenerics1<T>[] array1 = new TaskGenerics1[]{null};
        TaskGenerics1<Integer>[] array = new TaskGenerics1[0];
        return array;
    }

    public M[] getVal() {
        return val;
    }

    public <K, V> boolean compare(TaskGenerics1<T>[] p1, TaskGenerics2<T,M>[] p2) {
        return true;
    }


    public void variableTest() {
        TaskGenerics1<String> taskGenerics1 = new TaskGenerics1<String>("Hello");
        Integer arr[] = { 3, 6, 2, 8, 6 };
        TaskGenerics2<String, Integer> taskGenerics2 = new TaskGenerics2<>("Hello", arr);
    }

    @Override
    public void min() {
        System.out.println("Minimum");
    }

    @Override
    public void max() {
        System.out.println("Maximun");
    }

    @Override
    public void testGenericInteraface1(TaskGenerics1<T> ob1) {
        System.out.println("Interface Object");
    }

    @Override
    public T min1() {
        return null;
    }

    @Override
    public T max1() {
        return null;
    }
}
