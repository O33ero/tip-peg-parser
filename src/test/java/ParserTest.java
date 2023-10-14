import exception.UnrecognizedTokenException;
import lexer.TIPLexer;
import org.junit.jupiter.api.Test;
import statement.Statement;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    private static void payload(String code) {
        List<Statement> tokens = TIPLexer.tokenize(Arrays.stream(code.split("\n")));
        List<String> flat = TIPLexer.lineFlatter(Arrays.stream(code.split("\n"))).toList();

        System.out.println("Flat: " + flat);
        System.out.println("Tokens: " + tokens);
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
}
