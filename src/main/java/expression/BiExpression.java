package expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BiExpression implements Expression {
    private Expression left;
    private Expression right;
}
