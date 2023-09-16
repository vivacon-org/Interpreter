package org.hungdoan.lang.ast;

import org.hungdoan.lang.Location;

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

    @Override
    public String toString() {
        return "{" +
                "kind=" + kind +
                ", location=" + location +
                '}';
    }
}
