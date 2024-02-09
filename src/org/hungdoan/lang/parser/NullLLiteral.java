package org.hungdoan.lang.parser;

import org.hungdoan.lang.lexer.Location;

public class NullLLiteral extends Expression {
    public NullLLiteral(Location location) {
        super(NodeType.NUMERIC_LITERAL, location);
    }
}
