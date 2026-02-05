package airis.command;

import java.util.HashMap;

import airis.AirisException;

@FunctionalInterface
public interface CommandConstructor {
    Command apply(HashMap<String, String> t) throws AirisException;
}
