package Application;

public class Constraint<T> {
    private T lower_limit, upper_limit;

    public Constraint(T lower_limit, T upper_limit) {
        this.lower_limit = lower_limit;
        this.upper_limit = upper_limit;
    }

    public T getLower_limit() {
        return lower_limit;
    }

    public void setLower_limit(T lower_limit) {
        this.lower_limit = lower_limit;
    }

    public T getUpper_limit() {
        return upper_limit;
    }

    public void setUpper_limit(T upper_limit) {
        this.upper_limit = upper_limit;
    }
}
