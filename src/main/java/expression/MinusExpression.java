package expression;

public class MinusExpression extends BiExpression {
    public MinusExpression(Expression left, Expression right) {
        super(left, right);
    }


    @Override
    public String toString() {
        return String.format("%s - %s", getLeft(), getRight());
    }
}
