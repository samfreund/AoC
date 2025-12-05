package utils;

public class Tuple<X, Y> {
    public final X x;
    public final Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Tuple<X, Y> other) {
        return this.x.equals(other.x) && this.y.equals(other.y);
    }
}