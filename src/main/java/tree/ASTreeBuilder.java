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
            if (levelStack.peek() == 0) {
                levelStack.pop();
            }
            Statement current = statementStack.pop();
            switch (current) {
                case OutputStatement outputStatement -> {
                    stringBuilder.append(getOffset()).append("output: ").append(outputStatement.getExpression()).append("\n");
                    reduceLevelReminderOrPop();
                }
                case EqualStatement equalStatement -> {
                    stringBuilder.append(getOffset()).append(equalStatement.getId()).append(" = ").append(equalStatement.getExpression()).append("\n");
                    reduceLevelReminderOrPop();
                }
                case IfStatement ifStatement -> {
                    stringBuilder.append(getOffset()).append("if ").append(ifStatement.getCondition()).append(" is true ↴").append("\n");
                    reduceLevelReminderWithoutPop();
                    pushToStatementsStack(ifStatement);
                }
                case ElseStatement elseStatement -> {
                    stringBuilder.append(getOffset()).append("else ").append("↴").append("\n");
                    reduceLevelReminderWithoutPop();
                    pushToStatementsStack(elseStatement);
                }
                case WhileStatement whileStatement -> {
                    stringBuilder.append(getOffset()).append("while ").append(whileStatement.getCondition()).append(" is true ↴").append("\n");
                    reduceLevelReminderWithoutPop();
                    pushToStatementsStack(whileStatement);
                }
                default -> throw new IllegalArgumentException("Cannot resolve statement: " + current);
            }

        }

        return stringBuilder.toString();
    }

    private String getOffset() {
        return "\t".repeat(levelStack.size());
    }

    private void reduceLevelReminderOrPop() {
        int reminderStatementCountOnCurrentLevel = levelStack.pop() - 1;
        if (reminderStatementCountOnCurrentLevel > 0) {
            levelStack.push(reminderStatementCountOnCurrentLevel);
        }
    }

    private void reduceLevelReminderWithoutPop() {
        int reminderStatementCountOnCurrentLevel = levelStack.pop() - 1;
        levelStack.push(reminderStatementCountOnCurrentLevel);
    }

    private void pushToStatementsStack(StatementContainer statementContainer) {
        statementContainer.getBodyStatements().reversed().forEach(statementStack::push);
        levelStack.push(statementContainer.getBodyStatements().size());
    }
}
