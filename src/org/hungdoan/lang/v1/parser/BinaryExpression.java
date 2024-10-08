package org.hungdoan.lang.v1.parser;

import org.hungdoan.lang.v1.lexer.Location;

public class BinaryExpression extends Expression {
    private Expression left;

    private Expression right;

    private String operator;

    public BinaryExpression(Expression left, Expression right, String operator, Location location) {
        super(NodeType.BINARY_EXPRESSION, location);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
}
