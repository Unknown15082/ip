package airis.command;

import static airis.constants.TimeFormats.all;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import airis.AirisException;
import airis.storage.Storage;
import airis.task.Deadline;
import airis.task.TaskList;

public class DeadlineCommand implements Command {
    private final String description;
    private final LocalDateTime due;

    private DeadlineCommand(String description, LocalDateTime due) {
        this.description = description;
        this.due = due;
    }

    public static Command make(HashMap<String, String> args) throws AirisException {
        String description = args.get("main");
        if (description == null || description.isEmpty()) {
            throw new AirisException("Description cannot be empty");
        }

        String dueStr = args.get("/by");
        if (dueStr == null || dueStr.isEmpty()) {
            throw new AirisException("Due date cannot be empty");
        }

        LocalDateTime due;
        try {
            due = LocalDateTime.parse(dueStr, all);
        } catch (DateTimeParseException e) {
            throw new AirisException("Cannot parse date");
        }

        return new DeadlineCommand(description, due);
    }

    @Override
    public Response process(Storage storage, TaskList tasklist) {
        Deadline task = new Deadline(this.description, this.due);
        tasklist.add(task);
        return Response.fromMessage(
                "I have added this task to your list:\n\t" + task
        );
    }
}
