package org.hungdoan.lang.ast;

import org.hungdoan.lang.Location;

public class NumericLiteral extends Expression {

    private int value;

    public NumericLiteral(int value, Location location) {
        super(NodeType.NUMERIC_LITERAL, location);
        this.value = value;
    }
}
