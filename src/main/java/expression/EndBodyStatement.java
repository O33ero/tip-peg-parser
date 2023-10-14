package expression;

import statement.Statement;
import token.StmToken;

public class EndBodyStatement implements Statement {
    @Override
    public StmToken getToken() {
        return StmToken.END_STM;
    }

    @Override
    public String toString() {
        return "} ";
    }
}
