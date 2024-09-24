package org.hungdoan.lang.v1.parser;

import org.hungdoan.lang.v1.lexer.Location;

public class NumericLiteral extends Expression {

    private int value;

    public NumericLiteral(int value, Location location) {
        super(NodeType.NUMERIC_LITERAL, location);
        this.value = value;
    }
}
