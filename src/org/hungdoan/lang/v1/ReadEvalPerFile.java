package org.hungdoan.lang.v1;

import org.hungdoan.lang.v1.lexer.Lexer;
import org.hungdoan.lang.v1.lexer.Token;
import org.hungdoan.lang.v1.parser.Parser;
import org.hungdoan.lang.v1.parser.Program;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Deque;
import java.util.Iterator;

public class ReadEvalPerFile {

    public static void main(String[] args) {
        parser();
    }

    private static void parser() {
        String filePath = "./src/resource/test.viva";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Lexer lexer = new Lexer();
            Deque<Token> tokens = lexer.tokenize(reader);
            Program ast = new Parser(tokens).produceAST();
            System.out.println(ast);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void lexer() {
        String filePath = "./src/resource/test.viva";
        Deque<Token> tokenize = new Lexer().tokenize(filePath);
        Iterator<Token> tokens = tokenize.iterator();
        while (tokens.hasNext()) {
            Token token = tokens.next();
            System.out.print(token + "    ");
        }
        System.out.println();
    }
}
