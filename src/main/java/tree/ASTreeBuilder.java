package tree;

import statement.*;

import java.util.Stack;

public class ASTreeBuilder {
    private final RootStatement rootStatement;
    private final Stack<Integer> levelStack = new Stack<>();
    private final Stack<Statement> statementStack = new Stack<>();

    private ASTreeBuilder(RootStatement rootStatement) {
        this.rootStatement = rootStatement;
    }

    public static ASTree build(RootStatement rootStatement) {
        return new ASTreeBuilder(rootStatement).build();
    }

    private ASTree build() {
        return new ASTree(decomposeRoot(rootStatement));
    }

    private String decomposeRoot(RootStatement rootStatement) {
        StringBuilder stringBuilder = new StringBuilder("root ↴").append("\n");

        rootStatement.getBody().getStatements().reversed().forEach(statementStack::push);
        levelStack.push(rootStatement.getBody().getStatements().size());

        while (!statementStack.isEmpty()) {
            int currentLevel = levelStack.size();
            Statement current = statementStack.pop();
            reduceReminderOrPop();
            switch (current) {
                case OutputStatement outputStatement -> {
                    stringBuilder.append("\t".repeat(currentLevel)).append("output: ").append(outputStatement.getExpression()).append("\n");
                }
                case EqualStatement equalStatement -> {
                    stringBuilder.append("\t".repeat(currentLevel)).append(equalStatement.getId()).append(" = ").append(equalStatement.getExpression()).append("\n");
                }
                case IfStatement ifStatement -> {
                    stringBuilder.append("\t".repeat(currentLevel)).append("if ").append(ifStatement.getCondition()).append(" is true ↴").append("\n");
                    pushToStatementsStack(ifStatement);
                }
                case ElseStatement elseStatement -> {
                    stringBuilder.append("\t".repeat(currentLevel)).append("else ").append("↴").append("\n");
                    pushToStatementsStack(elseStatement);
                }
                case WhileStatement whileStatement -> {
                    stringBuilder.append("\t".repeat(currentLevel)).append("while ").append(whileStatement.getCondition()).append(" is true ↴").append("\n");
                    pushToStatementsStack(whileStatement);
                }
                default -> throw new IllegalArgumentException("Cannot resolve statement: " + current);
            }
        }

        return stringBuilder.toString();
    }

    private void reduceReminderOrPop() {
        int reminderStatementCountOnCurrentLevel = levelStack.peek() - 1;
        if (reminderStatementCountOnCurrentLevel == 0) {
            levelStack.pop();
        }
    }

    private void pushToStatementsStack(StatementContainer statementContainer) {
        statementContainer.getBodyStatements().reversed().forEach(statementStack::push);
        levelStack.push(statementContainer.getBodyStatements().size());
    }
}
