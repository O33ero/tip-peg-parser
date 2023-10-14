package statement;

import expression.Expression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import token.StmToken;
import token.Token;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class WhileStatement implements Statement {
    private final Expression condition;
    @Setter
    private List<Token> body;

    @Override
    public StmToken getToken() {
        return StmToken.WHILE_EXP;
    }

    @Override
    public String toString() {
        return String.format("while (%s) { %s }", condition, body);
    }
}
