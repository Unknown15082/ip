package airis.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import airis.AirisException;

/**
 * A parser to convert from text to Command
 */
public class Parser {
    private final HashMap<String, String[]> flagsList;
    private final HashMap<String, CommandConstructor> constructorList;

    public Parser() {
        this.flagsList = new HashMap<>();
        this.constructorList = new HashMap<>();
    }

    public static Parser makeDefaultParser() {
        Parser parser = new Parser();

        parser.register("bye", new String[]{}, ByeCommand::make);
        parser.register("list", new String[]{}, ListCommand::make);
        parser.register("todo", new String[]{}, TodoCommand::make);

        return parser;
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

    public void register(String name, String[] flags, CommandConstructor constructor) {
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
        CommandConstructor callable = constructorList.get(mainCommand);

        HashSet<String> flagSet = new HashSet<>(Arrays.asList(flags));
        HashMap<String, String> args = new HashMap<>();

        String currentFlag = "main";
        ArrayList<String> currentData = new ArrayList<>();
        for (int i = 1; i < tokens.length; i++) {
            String token = tokens[i];
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
