package org.hungdoan.lang.v1.parser;

import org.hungdoan.lang.v1.lexer.Token;
import org.hungdoan.lang.v1.lexer.TokenType;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;

public class Parser {

    private Deque<Token> tokens;

    private final Set<String> multiplying_operators;

    private final Set<String> additive_operators;

    public Parser() {
        this(new LinkedList<>());
    }

    public Parser(Deque<Token> tokens) {
        this.tokens = tokens;
        this.multiplying_operators = Set.of("/", "*", "%");
        this.additive_operators = Set.of("+", "-");
    }

    public void setTokens(Deque<Token> tokens) {
        this.tokens = tokens;
    }

    public Program produceAST() {
        Program program = new Program();
        while (!isNextTokenEOF()) {
            program.getBody().add(parseStatements());
        }
        return program;
    }

    // Handle complex statement types
    private Statement parseStatements() {
        // skip to parse_expr
        return parseExpressions();
    }

    private Expression parseExpressions() {
        return parseAdditiveExpressions();
    }

    private Expression parseAdditiveExpressions() {
        Expression leftSide = parseMultiplyingExpressions();
        String currentValue = seekToken().getValue();

        if (additive_operators.contains(currentValue)) {
            String operator = nextToken().getValue();
            Expression rightSide = parseMultiplyingExpressions();
            return new BinaryExpression(leftSide, rightSide, operator, leftSide.getLocation());
        }

        return leftSide;
    }

    private Expression parseMultiplyingExpressions() {
        Expression leftSide = parsePrimaryExpression();
        String currentValue = seekToken().getValue();

        if (multiplying_operators.contains(currentValue)) {
            String operator = nextToken().getValue();
            Expression rightSide = parsePrimaryExpression();
            return new BinaryExpression(leftSide, rightSide, operator, leftSide.getLocation());
        }

        return leftSide;
    }

    private Expression parsePrimaryExpression() {
        Token token = seekToken();

        switch (token.getType()) {

            case NUMBER: {
                Token currentToken = nextToken();
                return new NumericLiteral(Integer.parseInt(currentToken.getValue()), currentToken.getLocation());
            }

            case NULL: {
                Token currentToken = nextToken();
                return new NullLLiteral(currentToken.getLocation());
            }

            case IDENTIFIER: {
                Token currentToken = nextToken();
                return new Identifier(currentToken.getValue(), currentToken.getLocation());
            }
            case OPEN_PARENTHESIS: {
                nextToken();
                Expression expressionInParens = parseExpressions();
                expectToken(TokenType.CLOSE_PARENTHESIS, "Unexpected token found, expect Close Paren");
                return expressionInParens;
            }
            default: {
                throw new RuntimeException("Unexpected token found during parsing " + seekToken());
            }
        }
    }

    private Token seekToken() {
        return tokens.getFirst();
    }

    private boolean isNextTokenEOF() {
        return seekToken().getType().equals(TokenType.EOF);
    }

    private Token nextToken() {
        return tokens.pollFirst();
    }

    private Token expectToken(TokenType expectedType, String message) {
        Token token = tokens.pollFirst();
        if (token == null || !token.getType().equals(expectedType)) {
            throw new RuntimeException(message + " at location " + token.getLocation());
        }
        return token;
    }
}
