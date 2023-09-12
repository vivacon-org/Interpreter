package org.hungdoan.lang.ast;

public class Statement {

    protected NodeType kind;

    public Statement() {
    }
    
    public Statement(NodeType kind) {
        this.kind = kind;
    }
}
