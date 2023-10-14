package token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@AllArgsConstructor
public enum IOToken {
    INPUT("input (.*) ;"),          // input Exp
    OUTPUT("output (.*) ;")         // output Exp
    ;

    @Getter
    private final String name = "IO ;";
    private final String pattern;

    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }
}
