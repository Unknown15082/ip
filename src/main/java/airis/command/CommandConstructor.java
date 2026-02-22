package airis.command;

import java.util.HashMap;

import airis.AirisException;

/**
 * Functional interface for creating a command.
 */
@FunctionalInterface
public interface CommandConstructor {
    Command apply(HashMap<String, String> t) throws AirisException;
}
