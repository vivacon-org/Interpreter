package org.hungdoan.lang.ast;

import java.util.List;

public class Program extends Statement {

    private List<Statement> body;

    public Program() {
        this.kind = NodeType.PROGRAM;
    }

    public Program(List<Statement> body) {
        this.kind = NodeType.PROGRAM;
        this.body = body;
    }
}
