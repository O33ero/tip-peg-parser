package statement;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import token.StmToken;

@Getter
@Setter
@NoArgsConstructor
public class ElseStatement implements StatementContainer {
    private final BodyStatement body = new BodyStatement();
    private IfStatement ifStatement;

    @Override
    public StmToken getToken() {
        return StmToken.IF_ELSE;
    }

    @Override
    public String toString() {
        return String.format("else { %s ", body);
    }

    @Override
    public void put(Statement statement) {
        body.getList().add(statement);
    }
}
