package se.kth.resources.package3;

// An interface that extends Comparable
public interface MinMaxTest<T extends Comparable<T> > {
    // Declaring abstract methods
    // Method with no body is abstract method
    T min();
    T max();
}