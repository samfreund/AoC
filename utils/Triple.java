package utils;

import java.util.Objects;

public class Triple<X, Y, Z> {
    public final X x;
    public final Y y;
    public final Z z;
    private final int hash;

    public Triple(X x, Y y, Z z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.hash = Objects.hash(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Pair))
            return false;
        Triple<?, ?, ?> other = (Triple<?, ?, ?>) o;
        return Objects.equals(this.x, other.x) && Objects.equals(this.y, other.y) && Objects.equals(this.z, other.z);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public String toString() {
        return "[ " + x.toString() + " , " + y.toString() + " , " + z.toString() + " ]";
    }
}