package org.hungdoan.lang.v1.parser;

import org.hungdoan.lang.v1.lexer.Location;

public class Statement {

    protected NodeType kind;

    protected Location location;

    public Statement(NodeType kind, Location location) {
        this.kind = kind;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public NodeType getKind() {
        return kind;
    }

    @Override
    public String toString() {
        return "{" +
                "kind=" + kind +
                ", location=" + location +
                '}';
    }
}
