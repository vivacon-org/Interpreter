package org.hungdoan.lang;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Lexer {

    // let x = 45 + { foot + bar}
    // [ LetToken, IdentifierTk, EqualsToken, NumberToken ]
    private Map<String, TokenType> singleCharToTokenType;

    private Map<String, TokenType> keywords;

    public Lexer() {
        singleCharToTokenType = new HashMap<>();
        singleCharToTokenType.put("(", TokenType.OPEN_PARENTHESIS);
        singleCharToTokenType.put(")", TokenType.CLOSE_PARENTHESIS);
        singleCharToTokenType.put("+", TokenType.BINARY_OPERATOR);
        singleCharToTokenType.put("-", TokenType.BINARY_OPERATOR);
        singleCharToTokenType.put("*", TokenType.BINARY_OPERATOR);
        singleCharToTokenType.put("/", TokenType.BINARY_OPERATOR);
        singleCharToTokenType.put("=", TokenType.EQUAL);

        keywords = new HashMap<>();
        keywords.put("let", TokenType.LET);
    }

    public List<Token> tokenize(String sourceCode) {
        List<Token> tokens = new LinkedList<>();
        String[] splitValues = sourceCode.split("");

        for (int index = 0; index < splitValues.length; ) {
            String value = splitValues[index];

            // skippable chars
            if (isOmitted(value)) {
                index++;
                continue;
            }

            // single char
            if (singleCharToTokenType.containsKey(value)) {
                tokens.add(new Token(value, singleCharToTokenType.get(value)));
                index++;
                continue;
            }

            // multiple chars

            // number
            if (isDigit(value)) {
                StringBuilder numberBuilder = new StringBuilder();
                while (isDigit(value)) {
                    numberBuilder.append(value);
                    index++;

                    if (index >= splitValues.length) {
                        break;
                    }

                    value = splitValues[index];
                }

                String finalNumber = numberBuilder.toString();
                tokens.add(new Token(finalNumber, TokenType.NUMBER));
                continue;
            }

            // sequence chars
            if (isAlpha(value)) {
                StringBuilder wordBuilder = new StringBuilder();
                while (isAlpha(value)) {
                    wordBuilder.append(value);
                    index++;

                    if (index >= splitValues.length) {
                        break;
                    }

                    value = splitValues[index];
                }

                String finalWord = wordBuilder.toString();
                TokenType preservedType = keywords.get(finalWord);
                TokenType finalTokenType = preservedType == null ? TokenType.IDENTIFIER : preservedType;
                tokens.add(new Token(finalWord, finalTokenType));
                continue;
            }

            // undetermined char
            System.out.println("Unrecognized token " + value);
            index++;
        }
        return tokens;
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