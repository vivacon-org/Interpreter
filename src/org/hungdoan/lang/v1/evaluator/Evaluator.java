package org.hungdoan.lang.v1.evaluator;

import org.hungdoan.lang.v1.parser.Statement;

public class Evaluator {

    public RuntimeValue evaluate(Statement statement) {
        switch (statement.getKind()) {
            case PROGRAM: {
                evaluateProgram(statement);
                break;
            }
            case NUMERIC_LITERAL: {
                break;
            }
            case IDENTIFIER: {
                break;
            }
            case BINARY_EXPRESSION: {
                break;
            }
        }
        return null;
    }

    private void evaluateProgram(Statement statement) {

    }
}
