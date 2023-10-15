package statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import token.StmToken;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class RootStatement implements Statement, StatementContainer {
    private BodyStatement body = new BodyStatement();

    @Override
    public StmToken getToken() {
        return null;
    }

    @Override
    public void put(Statement statement) {
        body.getStatements().add(statement);
    }

    @Override
    public List<Statement> getBodyStatements() {
        return body.getStatements();
    }

    @Override
    public String toString() {
        return body.toString();
    }


}
