package airis.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import airis.AirisException;

public class ParserTest {
    private static Parser parser;

    @BeforeAll
    public static void setUpStandardParser() {
        parser = new Parser();

        parser.register("bye", new String[]{}, ByeCommand::make);
    }

    @Test
    public void testParse_bye() throws AirisException {
        Command cmd = parser.parse("bye");
        assertInstanceOf(ByeCommand.class, cmd);
    }

    @Test
    public void testParse_empty() {
        assertThrows(AirisException.class,
                () -> parser.parse(""));
    }

    @Test
    public void testParse_invalidCommand() {
        assertThrows(AirisException.class,
                () -> parser.parse("invalid command"));
    }
}
