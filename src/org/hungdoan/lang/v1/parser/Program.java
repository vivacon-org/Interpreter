package org.hungdoan.lang.v1.parser;

import org.hungdoan.lang.v1.lexer.Location;

import java.util.Deque;
import java.util.LinkedList;

public class Program extends Statement {

    private Deque<Statement> body;

    public Program() {
        super(NodeType.PROGRAM, new Location(0, 0, 0));
        this.body = new LinkedList<>();
    }

    public Program(Deque<Statement> body) {
        super(NodeType.PROGRAM, new Location(0, 0, 0));
        this.body = body;
    }

    public Deque<Statement> getBody() {
        return body;
    }
}
