package se.kth.resources.package1;

import se.kth.resources.package2.Task8;

import static se.kth.resources.package1.Task1.a;

public class Task2 extends Task8 {
    private final int b = 10;
    private int c;
    final static float f0 = 1.0f;

    public void method3(int t) {
        final int m = 10;
        final int lm = 19;
        this.c = a + m;
    }

    public final void method4(final int t) {
        final int m = 10;
        final int lm = 19;
        this.c = a;
    }

    public void method5(float f) {
        System.out.println("hello world");
    }

    public void method9(float f) {
        // System.out.println("hello world");
    }
}
