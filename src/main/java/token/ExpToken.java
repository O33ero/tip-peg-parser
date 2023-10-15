package token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

/**
 * <div> Exp -> Int | Id | Exp + Exp | Exp - Exp | Exp * Exp | Exp / Exp | Exp > Exp | Exp == Exp | ( Exp ) | input </div>
 **/
@RequiredArgsConstructor
public enum ExpToken implements Token {
    EXP_PLUS_EXP("(.*) \\+ (.*)"),        // Exp + Exp
    EXP_MINUS_EXP("(.*) \\- (.*)"),       // Exp - Exp
    EXP_MULTI_EXP("(.*) \\* (.*)"),       // Exp * Exp
    EXP_DIV_EXP("(.*) \\/ (.*)"),         // Exp / Exp
    EXP_GRT_EXP("(.*) \\> (.*)"),         // Exp > Exp
    EXP_EQ_EXP("(.*) == (.*)"),         // Exp == Exp
    EXP_BOX("\\( (.*) \\)"),            // ( Exp )
    INPUT_EXP("(input)")                     // input
    ;

    @Getter
    private final String name = "EXP";
    private final String pattern;

    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }
}
