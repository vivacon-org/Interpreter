package org.hungdoan.lang;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class KickOff {

    public static void main(String[] args) {

        System.out.println("Tokenizing process");
        Lexer lexer = new Lexer();
        String filePath = "./src/resource/test.hung";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = reader.readLine()) != null) {

                List<Token> tokenize = lexer.tokenize(line);
                Iterator<Token> tokens = tokenize.iterator();
                while (tokens.hasNext()) {
                    Token token = tokens.next();
                    System.out.print(token + "    ");
                }

                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
