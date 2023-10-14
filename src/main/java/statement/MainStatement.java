package statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import token.StmToken;

@Setter
@Getter
@RequiredArgsConstructor
public class MainStatement implements Statement, StatementContainer {
    private BodyStatement body = new BodyStatement();

    @Override
    public StmToken getToken() {
        return null;
    }

    @Override
    public void put(Statement statement) {
        body.getList().add(statement);
    }

    @Override
    public String toString() {
        return body.toString();
    }
}
