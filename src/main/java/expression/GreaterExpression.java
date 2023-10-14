package expression;


public class GreaterExpression extends BiExpression {
    public GreaterExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s > %s", getLeft(), getRight());
    }
}
