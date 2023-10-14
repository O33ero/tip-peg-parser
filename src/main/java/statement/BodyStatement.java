package statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import token.StmToken;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BodyStatement implements Statement {
    private List<Statement> statements = new ArrayList<>();

    @Override
    public StmToken getToken() {
        return StmToken.END_STM;
    }

    @Override
    public String toString() {
        return String.format("{ %s }", statements);
    }
}
