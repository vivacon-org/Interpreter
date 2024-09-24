package org.hungdoan.lang.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hungdoan.lang.v2.TokenType.*;

class Lexer {

    private int start_lexeme_offset = 0;

    private int current_offset = 0;

    private int current_line = 1;

    private final String source;

    private final List<Token> tokens;

    private static final Map<String, TokenType> keywords = new HashMap<>();

    Lexer(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();

        keywords.put("and", AND);
        keywords.put("class", CLASS);
        keywords.put("else", ELSE);
        keywords.put("false", FALSE);
        keywords.put("for", FOR);
        keywords.put("fun", FUN);
        keywords.put("if", IF);
        keywords.put("nil", NIL);
        keywords.put("or", OR);
        keywords.put("print", PRINT);
        keywords.put("return", RETURN);
        keywords.put("super", SUPER);
        keywords.put("this", THIS);
        keywords.put("true", TRUE);
        keywords.put("var", VAR);
        keywords.put("while", WHILE);
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start_lexeme_offset = current_offset;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, current_line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
                addToken(LEFT_PAREN);
                break;
            case ')':
                addToken(RIGHT_PAREN);
                break;
            case '{':
                addToken(LEFT_BRACE);
                break;
            case '}':
                addToken(RIGHT_BRACE);
                break;
            case ',':
                addToken(COMMA);
                break;
            case '.':
                addToken(DOT);
                break;
            case '-':
                addToken(MINUS);
                break;
            case '+':
                addToken(PLUS);
                break;
            case ';':
                addToken(SEMICOLON);
                break;
            case '*':
                addToken(STAR);
                break;
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '/':
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd()) {
                        advance();
                    }
                    break;
                }

                addToken(SLASH);
                break;
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;

            case '\n':
                current_line++;
                break;
            case '"':
                string();
                break;
            default:
                if (isDigit(c)) {
                    number();
                    return;
                }

                if (isAlpha(c)) {
                    identifier();
                    return;
                }

                Viva.error(current_line, "Unexpected character.");
                break;
        }
    }

    private void number() {
        while (isDigit(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (isDigit(peek())) advance();
        }

        addToken(NUMBER,
                Double.parseDouble(source.substring(start_lexeme_offset, current_offset)));
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') current_line++;
            advance();
        }

        if (isAtEnd()) {
            Viva.error(current_line, "Unterminated string.");
            return;
        }

        // The closing ".
        advance();

        // Trim the surrounding quotes.
        String value = source.substring(start_lexeme_offset + 1, current_offset - 1);
        addToken(STRING, value);
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current_offset);
    }

    private char peekNext() {
        if (current_offset + 1 >= source.length()) return '\0';
        return source.charAt(current_offset + 1);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current_offset) != expected) return false;

        current_offset++;
        return true;
    }

    private char advance() {
        return source.charAt(current_offset++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start_lexeme_offset, current_offset);
        tokens.add(new Token(type, text, literal, current_line));
    }

    private boolean isAtEnd() {
        return current_offset >= source.length();
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start_lexeme_offset, current_offset);
        TokenType type = keywords.get(text);
        if (type == null) {
            type = IDENTIFIER;
        }
        addToken(type);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
}
