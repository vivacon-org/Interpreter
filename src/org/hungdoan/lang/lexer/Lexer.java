package org.hungdoan.lang.lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Lexer {
    private Map<String, TokenType> singleCharToTokenType;
    private Map<String, TokenType> keywordToTokenType;
    private int startOffset;
    private int startLine;
    private int startColumn;

    public Lexer() {
        singleCharToTokenType = new HashMap<>();
        singleCharToTokenType.put("(", TokenType.OPEN_PARENTHESIS);
        singleCharToTokenType.put(")", TokenType.CLOSE_PARENTHESIS);
        singleCharToTokenType.put("+", TokenType.BINARY_OPERATOR);
        singleCharToTokenType.put("-", TokenType.BINARY_OPERATOR);
        singleCharToTokenType.put("*", TokenType.BINARY_OPERATOR);
        singleCharToTokenType.put("/", TokenType.BINARY_OPERATOR);
        singleCharToTokenType.put("=", TokenType.EQUAL);

        keywordToTokenType = new HashMap<>();
        keywordToTokenType.put("let", TokenType.LET);
        keywordToTokenType.put("const", TokenType.CONST);
        keywordToTokenType.put("null", TokenType.NULL);

        startOffset = 0;
        startLine = 0;
        startColumn = 0;
    }

    public Deque<Token> tokenize(BufferedReader reader) {
        try {
            String eachLineCodec = "";
            Deque<Token> tokens = new LinkedList<>();

            while ((eachLineCodec = reader.readLine()) != null) {
                tokens.addAll(tokenizeEachLine(eachLineCodec));
                startLine++;
            }

            tokens.add(new Token("End Of File", TokenType.EOF, startOffset, startLine, 0));
            return tokens;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Deque<Token> tokenize(String sourceCode) {
        String[] lines = sourceCode.split("\n");
        Deque<Token> tokens = new LinkedList<>();

        for (int index = 0; index < lines.length; index++) {
            String eachLine = lines[index];
            tokens.addAll(tokenizeEachLine(eachLine));
            startLine++;
        }

        tokens.add(new Token("End Of File", TokenType.EOF, startOffset, startLine, 0));
        return tokens;
    }

    private Deque<Token> tokenizeEachLine(String eachLineSource) {
        Deque<Token> resultTokens = new LinkedList<>();
        String[] charArray = eachLineSource.split("");
        for (startColumn = 0; startColumn < charArray.length; ) {
            String value = charArray[startColumn];

            // skip chars
            if (isOmitted(value)) {
                startColumn++;
                startOffset++;
                continue;
            }

            // single char
            if (singleCharToTokenType.containsKey(value)) {
                resultTokens.add(new Token(value, singleCharToTokenType.get(value), startOffset, startLine, startColumn));
                startColumn++;
                startOffset++;
                continue;
            }

            // multiple chars

            // number
            if (isDigit(value)) {
                StringBuilder numberBuilder = new StringBuilder();
                int originalStartOffset = startOffset;
                int originalStartColumn = startColumn;
                while (isDigit(value)) {
                    numberBuilder.append(value);
                    startColumn++;
                    startOffset++;

                    if (startColumn >= charArray.length) {
                        break;
                    }

                    value = charArray[startColumn];
                }

                String finalNumber = numberBuilder.toString();
                resultTokens.add(new Token(finalNumber, TokenType.NUMBER, originalStartOffset, startLine, originalStartColumn));
                continue;
            }

            // sequence chars
            if (isAlpha(value)) {
                StringBuilder wordBuilder = new StringBuilder();
                int originalStartOffset = startOffset;
                int originalStartColumn = startColumn;
                while (isAlpha(value)) {
                    wordBuilder.append(value);
                    startColumn++;
                    startOffset++;

                    if (startColumn >= charArray.length) {
                        break;
                    }

                    value = charArray[startColumn];
                }

                String finalWord = wordBuilder.toString();
                TokenType preservedType = keywordToTokenType.get(finalWord);
                TokenType finalTokenType = preservedType == null ? TokenType.IDENTIFIER : preservedType;
                resultTokens.add(new Token(finalWord, finalTokenType, originalStartOffset, startLine, originalStartColumn));
                continue;
            }

            // undetermined chars
            System.out.println("Unrecognized token " + value);
            startColumn++;
            startOffset++;
        }
        return resultTokens;
    }


    private boolean isDigit(String value) {
        return value.length() == 1 && Character.isDigit(value.charAt(0));
    }

    private boolean isAlpha(String value) {
        return value.matches("[a-zA-Z]");
    }

    private boolean isOmitted(String value) {
        return " ".equals(value) || "\n".equals(value) || "\t".equals(value);
    }
}