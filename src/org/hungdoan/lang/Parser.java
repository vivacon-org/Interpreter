package org.hungdoan.lang;

import org.hungdoan.lang.ast.BinaryExpression;
import org.hungdoan.lang.ast.CompilationUnit;
import org.hungdoan.lang.ast.Expression;
import org.hungdoan.lang.ast.Identifier;
import org.hungdoan.lang.ast.NumericLiteral;
import org.hungdoan.lang.ast.Statement;

import java.util.Arrays;
import java.util.Deque;

public class Parser {

    private Deque<Token> tokens;

    public Statement produceAST(String filePath) {
        Lexer lexer = new Lexer();
        CompilationUnit program = new CompilationUnit();
        tokens = lexer.tokenize(filePath);
        while (isNextTokenEOF() == false) {
            program.getBody().add(parseStatements());
        }
        return program;
    }

    // Handle complex statement types
    private Statement parseStatements() {
        // skip to parse_expr
        return parseExpression();
    }

    private Expression parseExpression() {
        return parseAdditiveExpression();
    }

    //((3 - 2) * 5)
    private Expression parseAdditiveExpression() {
        Expression leftSide = parseMultitiveExpression();
        String currentValue = seekToken().getValue();
        String[] operators = {"+", "-"};
        if (Arrays.asList(operators).contains(currentValue)) {
            String operator = nextToken().getValue();
            Expression rightSide = parseMultitiveExpression();
            return new BinaryExpression(leftSide, rightSide, operator, leftSide.getLocation());
        }
        return leftSide;
    }

    //((3 - 2) * 5)
    private Expression parseMultitiveExpression() {
        Expression leftSide = parsePrimaryExpression();
        String currentValue = seekToken().getValue();
        String[] operators = {"/", "*", "%"};
        if (Arrays.asList(operators).contains(currentValue)) {
            String operator = nextToken().getValue();
            Expression rightSide = parsePrimaryExpression();
            return new BinaryExpression(leftSide, rightSide, operator, leftSide.getLocation());
        }
        return leftSide;
    }

    //((3 - 2) * 5)
    private Expression parsePrimaryExpression() {
        Token token = seekToken();
        Expression finalExpression = null;

        switch (token.getType()) {

            case NUMBER: {
                Token currentToken = nextToken();
                finalExpression = new NumericLiteral(Integer.parseInt(currentToken.getValue()), currentToken.getLocation());
                break;
            }
            case IDENTIFIER: {
                Token currentToken = nextToken();
                finalExpression = new Identifier(currentToken.getValue(), currentToken.getLocation());
                break;
            }
            case OPEN_PARENTHESIS: {
                nextToken();
                Expression expressionInParens = parseExpression();
                expectToken(TokenType.CLOSE_PARENTHESIS, "Unexpected token found, expect Close Paren");
                finalExpression = expressionInParens;
                break;
            }
            default: {
                System.out.println("Unexpected token found during parsing " + seekToken());
            }
        }
        return finalExpression;
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
        if (token == null || token.getType().equals(expectedType) == false) {
            System.out.println(message);
        }
        return token;
    }
}
