package statement;

import expression.Expression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import token.StmToken;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class IfStatement implements Statement, StatementContainer {
    private final Expression condition;
    private final BodyStatement body = new BodyStatement();

    @Override
    public StmToken getToken() {
        return StmToken.IF_EXP;
    }

    @Override
    public String toString() {
        return String.format("if (%s) %s ", condition, body);
    }

    @Override
    public void put(Statement statement) {
        body.getStatements().add(statement);
    }

    @Override
    public List<Statement> getBodyStatements() {
        return body.getStatements();
    }
}
