package lexer;

import exception.CannotRecognizeTokenException;
import exception.LexicalException;
import exception.UnrecognizedTokenException;
import expression.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import statement.*;
import token.AtomicToken;
import token.ExpToken;
import token.StmToken;
import token.Token;

import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TIPLexer {
    private static final Logger LOGGER = LogManager.getLogger();

    private TIPLexer() {
    }

    public static MainStatement tokenize(Stream<String> lines) {
        List<String> flatedLines = lineFlatter(lines).toList();
        List<Statement> statementToken = statementLexer(flatedLines);
        MainStatement mainStatement = statementGrouper(statementToken);

        return mainStatement;
    }

    public static MainStatement statementGrouper(List<Statement> statementToken) {
        Iterator<Statement> iterator = statementToken.iterator();
        Stack<StatementContainer> headStatement = new Stack<>();
        headStatement.push(new MainStatement());

        while (iterator.hasNext()) {
            Statement current = iterator.next();
            switch (current) {
                case IfStatement ifStatement -> headStatement.push(ifStatement);
                case WhileStatement whileStatement -> headStatement.push(whileStatement);
                case ElseStatement elseStatement -> headStatement.push(elseStatement);
                case EndBodyStatement ignored -> {
                    StatementContainer currentHead = headStatement.pop();
                    headStatement.peek().put(currentHead);
                }
                // IOStatement -> default
                // EqualsStatement -> default
                default -> headStatement.peek().put(current);
            }
        }

        if (!(headStatement.size() == 1 && headStatement.peek() instanceof MainStatement)) {
            throw new LexicalException("Cannot to resolve: " + headStatement.peek());
        }

        return (MainStatement) headStatement.pop();
    }

    public static List<Statement> statementLexer(List<String> lines) {
        Iterator<String> iterator = lines.iterator();
        List<Statement> output = new ArrayList<>();

        String prev = "";
        while (iterator.hasNext()) {
            String next = prev + " " + iterator.next();

            try {
                Statement statement = tryToGuessStatement(next);
                output.add(statement);

                LOGGER.trace("Resolve token: {}", next);
                prev = next.replaceFirst(statement.getToken().getPattern().pattern(), "").trim();
            } catch (CannotRecognizeTokenException ex) {
                LOGGER.trace("Cannot recognize token: {}", ex.getMessage());
                prev = next;
            }
        }

        if (!prev.isBlank()) {
            LOGGER.error("Cannot recognize statement: {}", prev);
            throw new UnrecognizedTokenException("Cannot recognize statement: " + prev);
        }

        return output;
    }

    public static Stream<String> lineFlatter(Stream<String> lines) {
        return lines
                .map(line -> line.replace("(", " ( "))
                .map(line -> line.replace(")", " ) "))
                .map(line -> line.replace(";", " ; "))
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .filter(line -> !line.isBlank());
    }

    private static Statement tryToGuessStatement(String str) {
        return Arrays.stream(StmToken.values()).collect(Collectors.toMap(v -> v, Token::getPattern)).entrySet()
                .stream()
                .filter(token -> {
                    Matcher matcher = token.getValue().matcher(str);
                    return matcher.find();
                }).findFirst()
                .map(token -> {
                            Matcher matcher = token.getValue().matcher(str);
                            if (matcher.find()) {
                                return switch (token.getKey()) {
                                    case IF_EXP -> new IfStatement(tryToGuessExpression(matcher.group(1)));
                                    case WHILE_EXP -> new WhileStatement(tryToGuessExpression(matcher.group(1)));
                                    case IF_ELSE -> new ElseStatement();
                                    case IO_EXP -> new IOStatement(matcher.group(1), tryToGuessExpression(matcher.group(2)));
                                    case END_STM -> new EndBodyStatement();
                                    case ID_EQ_EXP ->
                                            new EqualStatement(matcher.group(1), tryToGuessExpression(matcher.group(2)));
                                };
                            }
                            throw new IllegalArgumentException("Cannot resolve statement: " + token.getKey().name());
                        }
                )
                .orElseThrow(() -> new CannotRecognizeTokenException(str));
    }

    private static Expression tryToGuessExpression(String str) {
        return Arrays.stream(ExpToken.values()).collect(Collectors.toMap(v -> v, Token::getPattern)).entrySet()
                .stream()
                .filter(token -> {
                    Matcher matcher = token.getValue().matcher(str);
                    return matcher.find();
                }).findFirst()
                .map(token -> {
                    Matcher matcher = token.getValue().matcher(str);
                    if (matcher.find()) {
                        return switch (token.getKey()) {
                            case EXP_BOX -> new BoxExpression(tryToGuessExpression(matcher.group(1)));
                            case EXP_PLUS_EXP ->
                                    new PlusExpression(tryToGuessExpression(matcher.group(1)), tryToGuessExpression(matcher.group(2)));
                            case EXP_MINUS_EXP ->
                                    new MinusExpression(tryToGuessExpression(matcher.group(1)), tryToGuessExpression(matcher.group(2)));
                            case EXP_MULTI_EXP ->
                                    new MultiExpression(tryToGuessExpression(matcher.group(1)), tryToGuessExpression(matcher.group(2)));
                            case EXP_DIV_EXP ->
                                    new DivExpression(tryToGuessExpression(matcher.group(1)), tryToGuessExpression(matcher.group(2)));
                            case EXP_EQ_EXP ->
                                    new EqualExpression(tryToGuessExpression(matcher.group(1)), tryToGuessExpression(matcher.group(2)));
                            case EXP_GRT_EXP ->
                                    new GreaterExpression(tryToGuessExpression(matcher.group(1)), tryToGuessExpression(matcher.group(2)));
                        };
                    }
                    throw new IllegalArgumentException("Cannot resolve expression: " + token.getKey().name());
                })
                .orElse(tryToGuessAtomic(str));
    }

    private static AtomicExpression tryToGuessAtomic(String str) {
        return Arrays.stream(AtomicToken.values()).collect(Collectors.toMap(v -> v, Token::getPattern)).entrySet()
                .stream()
                .filter(token -> {
                    Matcher matcher = token.getValue().matcher(str);
                    return matcher.find();
                })
                .findFirst()
                .map(token -> {
                    Matcher matcher = token.getValue().matcher(str);
                    if (matcher.find()) {
                        return switch (token.getKey()) {
                            case ID -> new IdExpression(matcher.group(1));
                            case INT -> new IntExpression(matcher.group(1));
                        };
                    }
                    throw new IllegalArgumentException("Cannot resolve atomic expression: " + token.getKey().name());
                })
                .orElseThrow(() -> new CannotRecognizeTokenException(str));
    }
}
