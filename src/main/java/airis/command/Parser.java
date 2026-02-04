package airis.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

import airis.AirisException;

/**
 * A parser to convert from text to Command
 */
public class Parser {
    private final HashMap<String, String[]> flagsList;
    private final HashMap<String, Function<HashMap<String, String>, Command>> constructorList;

    public Parser() {
        this.flagsList = new HashMap<>();
        this.constructorList = new HashMap<>();
    }

    /**
     * Split the information text into tokens, separated by whitespaces
     *
     * @param text The information text
     * @return The list of tokens
     */
    static String[] getTokens(String text) {
        String[] tokens = text.split("\\s+");
        return Arrays.stream(tokens).filter(token -> !token.isEmpty()).toArray(String[]::new);
    }

    public void register(String name, String[] flags, Function<HashMap<String, String>, Command> constructor) {
        this.flagsList.put(name, flags);
        this.constructorList.put(name, constructor);
    }

    /**
     * Parse a line of text containing a command, and return the corresponding Command object.
     *
     * @param text The space-separated text.
     * @return The Command object created from the given data.
     * @throws AirisException if command is not found or no command was given.
     */
    public Command parse(String text) throws AirisException {
        String[] tokens = Parser.getTokens(text);

        String mainCommand;
        try {
            mainCommand = tokens[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AirisException("No command was specified");
        }

        if (!flagsList.containsKey(mainCommand) || !constructorList.containsKey(mainCommand)) {
            throw new AirisException("Command not found");
        }
        String[] flags = flagsList.get(mainCommand);
        Function<HashMap<String, String>, Command> callable = constructorList.get(mainCommand);

        HashSet<String> flagSet = new HashSet<>(Arrays.asList(flags));
        HashMap<String, String> args = new HashMap<>();

        String currentFlag = "main";
        ArrayList<String> currentData = new ArrayList<>();
        for (String token : tokens) {
            if (flagSet.contains(token)) {
                args.put(currentFlag, String.join(" ", currentData));
                currentFlag = token;
                continue;
            }
            currentData.add(token);
        }
        args.put(currentFlag, String.join(" ", currentData));

        return callable.apply(args);
    }
}
