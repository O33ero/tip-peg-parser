package token;

import java.util.regex.Pattern;

public interface Token {
    String getName();
    Pattern getPattern();
}
