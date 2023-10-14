package statement;

import expression.Expression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import token.StmToken;

@AllArgsConstructor
@Getter
public class EqualStatement implements Statement {
    private String id;
    private Expression expression;

    @Override
    public StmToken getToken() {
        return StmToken.ID_EQ_EXP;
    }

    @Override
    public String toString() {
        return String.format("%s = %s ; ", id, expression);
    }
}
