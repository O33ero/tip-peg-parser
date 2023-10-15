package token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

/**
 * <div> Stm → Id = Exp ; | output Exp ; | Stm Stm | if ( Exp ) { Stm } else { Stm } | while ( Exp ) { Stm } </div>
 **/
@Getter
@AllArgsConstructor
public enum StmToken implements Token {
    IF_EXP("IF_EXP", "if \\( (.*) \\)"),
    IF_ELSE("ELSE_STM", "else "),
    WHILE_EXP("WHILE_EXP_THEN_STM", "while \\( (.*) \\)"),
    END_STM("WHILE_EXP_THEN_STM", "\\{(.*)\\}"),
    ID_EQ_EXP("ID_EQ_EXP", "([a-zA-Z]+) \\= (.*) ;"),
    OUTPUT_EXP("OUTPUT_EXP", "(output) (.*) ;")
    ;

    private final String name;
    private final String pattern;

    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }
}
