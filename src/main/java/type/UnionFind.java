package type;

import statement.RootStatement;
import statement.Statement;
import statement.StatementContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// - арифметические операции применимы только к целым;
// - операции сравнения применимы только к выражениям с одинаковыми типами;
// - результатом выполнения выражения input и инструкции output могут быть только целые
// - выражения условий могут быть только целыми
// - операция вызова применима только к функциям
// - операция разыменования * применима только к указателям
public class UnionFind {
    private Map<Statement, Statement> buckets = new HashMap<>();

    private UnionFind() {
    }

    public static UnionFind of(RootStatement root) {
        UnionFind uf = new UnionFind();
        uf.processStatements(root.getBodyStatements());

        return uf;
    }

    private void processStatements(List<Statement> statements) {
        for (Statement statement : statements) {
            this.makeSet(statement);
            if (statement instanceof StatementContainer localRoot) {
                this.processStatements(localRoot.getBodyStatements(), localRoot);
            }
        }
    }

    private void processStatements(List<Statement> statements, Statement root) {
        for(Statement statement : statements) {
            this.makeSet(statement);
            this.union(statement, root);
            if (statement instanceof StatementContainer localRoot) {
                this.processStatements(localRoot.getBodyStatements(), localRoot);
            }
        }
    }

    public void makeSet(Statement statement) {
        buckets.put(statement, statement);
    }

    public Statement find(Statement statement) {
        if (buckets.get(statement).equals(statement)) {
            return statement;
        } else {
            Statement result = find(buckets.get(statement));
            buckets.replace(statement, result);
            return result;
        }
    }

    public void union(Statement s1, Statement s2) {
        var a = find(s1);
        var b = find(s2);
        if (!a.equals(b)) {
            buckets.replace(a, b);
        }
    }

    @Override
    public String toString() {
        return buckets.toString();
    }
}
