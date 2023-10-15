package expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IntExpression implements AtomicExpression {
    private String value;

    @Override
    public String toString() {
        return value;
    }
}
