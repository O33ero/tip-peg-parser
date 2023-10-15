package expression;

public class EqualExpression extends BiExpression {
    public EqualExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s == %s", getLeft(), getRight());
    }
}
