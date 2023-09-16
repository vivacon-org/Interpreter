package org.hungdoan.lang;

import org.hungdoan.lang.ast.Statement;

import java.util.Deque;
import java.util.Iterator;

public class KickOff {

    public static void main(String[] args) {
        parser();
    }

    private static void parser() {
        String filePath = "./src/resource/test.hung";
        Statement statement = new Parser().produceAST(filePath);
    }

    private static void lexer() {
        String filePath = "./src/resource/test.hung";
        Deque<Token> tokenize = new Lexer().tokenize(filePath);
        Iterator<Token> tokens = tokenize.iterator();
        while (tokens.hasNext()) {
            Token token = tokens.next();
            System.out.print(token + "    ");
        }
        System.out.println();
    }
}
