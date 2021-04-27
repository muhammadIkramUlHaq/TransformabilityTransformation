package se.kth.resources.package2;

import static se.kth.resources.package1.Task1.a;

public class Task6 extends Task5 {
    private final int b = 10;
    private int c;

    public void method3(int t) {
        final int m = 10;
        final int lm = 19;
        c = a;
    }

    public final void methodTask64(final int t) {
        final int m = 10;
        final int lm = 19;
        c = a;
    }

    public void method5(float f) {
        System.out.println("hello world");
    }
}
