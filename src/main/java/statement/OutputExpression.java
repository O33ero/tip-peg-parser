package statement;

import expression.Expression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import token.StmToken;

@Getter
@AllArgsConstructor
public class OutputExpression implements Statement {
    private String operation;
    private Expression expression;

    @Override
    public StmToken getToken() {
        return StmToken.OUTPUT_EXP;
    }

    @Override
    public String toString() {
        return String.format("%s %s ; ", operation, expression);
    }
}
