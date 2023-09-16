package org.hungdoan.lang;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Lexer {

    // let x = 45 + { foot + bar}
    // [ LetToken, IdentifierTk, EqualsToken, NumberToken ]
    private Map<String, TokenType> singleCharToTokenType;
    private Map<String, TokenType> keywordToTokenType;
    private int startOffset = 0;
    private int startLine = 0;
    private int startColumn = 0;

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
    }

    public Deque<Token> tokenize(String filePath) {

        Deque<Token> tokens = new LinkedList<>();
        String eachLineCode = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            while ((eachLineCode = reader.readLine()) != null) {
                tokens = tokenizeEachLineOfCode(eachLineCode, tokens);
                startLine++;
            }
            tokens.add(new Token("End Of File", TokenType.EOF, startOffset, startLine, 0));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokens;
    }

    private Deque<Token> tokenizeEachLineOfCode(String eachLineSource, Deque<Token> token) {
        Deque<Token> resultTokens = new LinkedList<>(token);
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
                int originalStartOffset = this.startOffset;
                int originalStartColumn = startColumn;
                while (isDigit(value)) {
                    numberBuilder.append(value);
                    startColumn++;
                    this.startOffset++;

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
                int originalStartOffset = this.startOffset;
                int originalStartColumn = startColumn;
                while (isAlpha(value)) {
                    wordBuilder.append(value);
                    startColumn++;
                    this.startOffset++;

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

            // undetermined char
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