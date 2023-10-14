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
public class IfStatement implements Statement {
    private final Expression condition;
    @Setter
    private List<Token> body;
    @Setter
    private ElseStatement elseStatement;

    @Override
    public StmToken getToken() {
        return StmToken.IF_EXP;
    }

    @Override
    public String toString() {
        return String.format("[if (%s) { %s }]", condition, body);
    }
}
