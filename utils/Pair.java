package utils;

import java.util.Objects;

public class Pair<X, Y> {
    public final X x;
    public final Y y;
    private final int hash;

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
        this.hash = Objects.hash(this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> other = (Pair<?, ?>) o;
        return Objects.equals(this.x, other.x) && Objects.equals(this.y, other.y);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public String toString() {
        return "[ " + x.toString() + " , " + y.toString() + " ]";
    }
}