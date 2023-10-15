package token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@AllArgsConstructor
public enum AtomicToken implements Token {
    INT("INT", "([a-zA-Z]+)"),    // Int
    ID("ID", "([\\-0-9]+)"),          // Id
    INPUT("INPUT", "input")       // input
    ;

    @Getter
    private final String name;
    private final String pattern;

    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }
}
