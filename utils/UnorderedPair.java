package utils;

import java.util.Objects;

public class UnorderedPair<X, Y> {
    public final X x;
    public final Y y;
    private final int hash;

    public UnorderedPair(X x, Y y) {
        this.x = x;
        this.y = y;
        this.hash = Objects.hash(this.x) + Objects.hash(this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof UnorderedPair)) return false;
        UnorderedPair<?,?> other = (UnorderedPair<?, ?>) o;
        return this.hashCode() == other.hashCode();
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