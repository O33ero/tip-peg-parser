package statement;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import token.StmToken;
import token.Token;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ElseStatement implements Statement {
    private List<Token> body;
    private IfStatement ifStatement;

    @Override
    public StmToken getToken() {
        return StmToken.IF_ELSE;
    }

    @Override
    public String toString() {
        return String.format("[else { %s }]", body);
    }
}
