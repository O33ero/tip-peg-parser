package statement;

import java.util.List;

public interface StatementContainer extends Statement {
    void put(Statement statement);
    List<Statement> getBodyStatements();
}
