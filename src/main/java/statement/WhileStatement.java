package statement;

import expression.Expression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import token.StmToken;

@Getter
@RequiredArgsConstructor
public class WhileStatement implements StatementContainer {
    private final Expression condition;
    private final BodyStatement body = new BodyStatement();

    @Override
    public StmToken getToken() {
        return StmToken.WHILE_EXP;
    }

    @Override
    public String toString() {
        return String.format("while (%s) %s ", condition, body);
    }

    @Override
    public void put(Statement statement) {
        body.getStatements().add(statement);
    }
}
