package statement;

import token.StmToken;

public class BodyStatement implements Statement {
    @Override
    public StmToken getToken() {
        return StmToken.END_STM;
    }

    @Override
    public String toString() {
        return "[ { } ]";
    }
}
