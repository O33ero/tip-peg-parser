package expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BoxExpression implements Expression {
    private Expression expression;

    @Override
    public String toString() {
        return String.format("[ ( %s ) ]", expression);
    }
}
