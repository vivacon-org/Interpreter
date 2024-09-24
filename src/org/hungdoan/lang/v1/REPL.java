package org.hungdoan.lang.v1;

import org.hungdoan.lang.v1.lexer.Lexer;
import org.hungdoan.lang.v1.lexer.Token;
import org.hungdoan.lang.v1.parser.Parser;
import org.hungdoan.lang.v1.parser.Program;

import java.util.Deque;
import java.util.Scanner;

/**
 * Read eval print loop purpose
 */
public class REPL {

    // let x = 45 + { foot + bar}
    // (1 + 3 + 2 * (4 +5))
    // [ LetToken, IdentifierTk, EqualsToken, NumberToken ]
    public static void main(String[] args) {
        System.out.println("\nRepl v0.1");
        Scanner scanner = new Scanner(System.in);
        Lexer lexer = new Lexer();
        Parser parser = new Parser();

        // Continue Repl Until User Stops Or Types `exit`
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            // Check for no user input or exit keyword.
            if (input != null || input.contains("exit")) {
                return;
            }

            Deque<Token> tokens = lexer.tokenize(input);
            parser.setTokens(tokens);
            Program ast = parser.produceAST();

            System.out.println(ast);
            // evaluate(ast);
        }
    }
}
