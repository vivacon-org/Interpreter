package org.hungdoan.lang.v1.parser;

import org.hungdoan.lang.v1.lexer.Location;

public class NullLLiteral extends Expression {
    public NullLLiteral(Location location) {
        super(NodeType.NUMERIC_LITERAL, location);
    }
}
