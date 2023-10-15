package expression;

public class PlusExpression extends BiExpression {
    public PlusExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("%s + %s", getLeft(), getRight());
    }
}
