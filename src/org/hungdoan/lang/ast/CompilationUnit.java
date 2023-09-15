package org.hungdoan.lang.ast;

import java.util.Deque;
import java.util.LinkedList;

public class CompilationUnit extends Statement {

    private Deque<Statement> body;

    public CompilationUnit() {
        this.kind = NodeType.PROGRAM;
        this.body = new LinkedList<>();
    }

    public CompilationUnit(Deque<Statement> body) {
        this.kind = NodeType.PROGRAM;
        this.body = body;
    }

    public Deque<Statement> getBody() {
        return body;
    }
}
