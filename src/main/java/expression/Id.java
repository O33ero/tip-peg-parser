package expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Id implements AtomicExpression{
    private String value;

    @Override
    public String toString() {
        return value;
    }
}
