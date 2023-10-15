package expression;

public class MultiExpression extends BiExpression {
    public MultiExpression(Expression left, Expression right) {
        super(left, right);
    }


    @Override
    public String toString() {
        return String.format("%s * %s", getLeft(), getRight());
    }
}
