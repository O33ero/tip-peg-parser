package expression;

import lombok.Getter;

@Getter
public class InputExpression implements AtomicExpression {
    private final String value = "input";

    @Override
    public String toString() {
        return value;
    }
}
