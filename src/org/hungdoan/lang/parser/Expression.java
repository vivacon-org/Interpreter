package org.hungdoan.lang.parser;

import org.hungdoan.lang.lexer.Location;

public class Expression extends Statement {

    public Expression(NodeType nodeType, Location location) {
        super(nodeType, location);
    }
}
