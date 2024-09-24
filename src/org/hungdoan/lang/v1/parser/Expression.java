package org.hungdoan.lang.v1.parser;

import org.hungdoan.lang.v1.lexer.Location;

public class Expression extends Statement {

    public Expression(NodeType nodeType, Location location) {
        super(nodeType, location);
    }
}
