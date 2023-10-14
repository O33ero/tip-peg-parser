package expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdExpression implements AtomicExpression{
    private String value;

    @Override
    public String toString() {
        return value;
    }
}
