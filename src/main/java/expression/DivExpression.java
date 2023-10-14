package expression;

public class DivExpression extends BiExpression {
    public DivExpression(Expression left, Expression right) {
        super(left, right);
    }


    @Override
    public String toString() {
        return String.format("[ %s / %s]", getLeft(), getRight());
    }
}
