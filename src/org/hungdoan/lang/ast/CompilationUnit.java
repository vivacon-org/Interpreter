package org.hungdoan.lang.ast;

import org.hungdoan.lang.Location;

import java.util.Deque;
import java.util.LinkedList;

public class CompilationUnit extends Statement {

    private Deque<Statement> body;

    public CompilationUnit() {
        super(NodeType.PROGRAM, new Location(0, 0, 0));
        this.body = new LinkedList<>();
    }

    public CompilationUnit(Deque<Statement> body) {
        super(NodeType.PROGRAM, new Location(0, 0, 0));
        this.body = body;
    }

    public Deque<Statement> getBody() {
        return body;
    }
}
