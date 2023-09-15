package org.hungdoan.lang.ast;

public class BinaryExpression extends Expression {
    private Expression left;

    private Expression right;

    private String operator;

    public BinaryExpression() {
        super(NodeType.BINARY_EXPRESSION);
    }

    public BinaryExpression(Expression left, Expression right, String operator) {
        super(NodeType.BINARY_EXPRESSION);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
}
