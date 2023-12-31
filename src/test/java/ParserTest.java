import exception.LexicalException;
import exception.UnexpectedElseStatement;
import exception.UnrecognizedTokenException;
import lexer.TIPLexer;
import org.junit.jupiter.api.Test;
import statement.RootStatement;
import statement.Statement;
import tree.ASTree;
import tree.ASTreeBuilder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    private static void payload(String code) {
        List<String> flat = TIPLexer.lineFlatter(Arrays.stream(code.split("\n"))).toList();
        List<Statement> lexer = TIPLexer.statementLexer(flat);
        RootStatement root = TIPLexer.statementGrouper(lexer);

        ASTree tree = ASTreeBuilder.build(root);

        System.out.println("Flat: " + flat);
        System.out.println("Tokens: " + lexer);
        System.out.println("Grouped: " + root);
        System.out.println(tree);
    }

    @Test
    void test1() {
        String code = """
                x = 1;
                y = 2;
                z = x + y;
                output z;
                """;
        assertDoesNotThrow(() -> payload(code));
    }

    @Test
    void test2() {
        String code = """
                x = 10;
                while (x > 0) {
                    output x;
                    x = x - 1;
                }
                """;
        assertDoesNotThrow(() -> payload(code));
    }

    @Test
    void test3() {
        String code = """
                x = 1;
                y = 2;
                if (x > y) {
                    output x;
                } else {
                    output y;
                }
                """;
        assertDoesNotThrow(() -> payload(code));
    }

    @Test
    void test4() {
        String code = """
                x = 10; if (x > 9) { output x; } else { output 0; } 
                """;
        assertDoesNotThrow(() -> payload(code));
    }

    @Test
    void test5() {
        String code = """
                x = 10; 
                if (x > 9) zzz { 
                    output x; 
                } else { 
                    output 0; 
                } 
                """;
        assertThrows(UnrecognizedTokenException.class, () -> payload(code), "Cannot resolve statement: zzz");
    }

    @Test
    void test6() {
        String code = """
                x = 10; 
                if (x > 9) { 
                    output x;
                } 
                else : {
                    output 0; 
                    } 
                """;
        assertThrows(UnrecognizedTokenException.class, () -> payload(code));
    }

    @Test
    void test7() {
        String code = """
                x = 10;           
                else  {
                    output x; 
                    } 
                """;
        assertThrows(UnexpectedElseStatement.class, () -> payload(code));
    }

    @Test
    void test8() {
        String code = """
                x = 10;           
                if (x > 10)  {
                    output x; 
                    } 
                """;
        assertDoesNotThrow(() -> payload(code));
    }

    @Test
    void test9() {
        String code = """
                x = 10;           
                if (x > 10) while (x > 10)  {
                    output x; 
                    } 
                """;
        assertThrows(LexicalException.class, () -> payload(code));
    }

    @Test
    void test10() {
        String code = """
                x = 10;           
                if (x > 10)   { 
                    } 
                """;
        assertDoesNotThrow(() -> payload(code));
    }

    @Test
    void test11() {
        String code = """
                x = 10;           
                if (x > input)   { 
                    output x;
                } else {
                    output 0;
                } 
                """;
        assertDoesNotThrow(() -> payload(code));
    }

    @Test
    void test12() {
        String code = """
                x = 10;           
                if (x > input) { 
                    while (input > 10) {
                        output x;
                    }
                } else {
                    while (input > 10) {
                        output 0;
                    }
                } 
                """;
        assertDoesNotThrow(() -> payload(code));
    }

    @Test
    void test13() {
        String code = """
                x = 10;           
                if (x > input) { 
                    while (input > 10) {
                        output x;
                    }
                } else {
                    while (input < 10) {
                        output 0;
                    }
                } 
                """;
        assertThrows(UnrecognizedTokenException.class, () -> payload(code));
    }
}
