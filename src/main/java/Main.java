import lexer.TIPLexer;
import statement.RootStatement;
import tree.ASTreeBuilder;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Grammatical:
 * <div> Int -> 0 | 1 | -1 | 2 | -2 | ... </div>
 * <div> Id -> x | y | z | ... </div>
 * <div> Exp -> Int | Id | Exp + Exp | Exp - Exp | Exp * Exp | Exp / Exp | Exp > Exp | Exp == Exp | ( Exp ) | input </div>
 * <div> Stm → Id = Exp ; | output Exp ; | Stm Stm | if ( Exp ) { Stm } else { Stm } | while ( Exp ) { Stm } </div>
 */
public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            RootStatement rootStatement = TIPLexer.tokenize(reader.lines());
            System.out.println(ASTreeBuilder.build(rootStatement));
        } catch (Exception ex) {
            System.out.printf("ERROR: %s", ex.getMessage());
        }
    }
}