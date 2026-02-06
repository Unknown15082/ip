package airis.command;

import static airis.constants.TimeFormats.all;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import airis.AirisException;
import airis.storage.Storage;
import airis.task.Event;
import airis.task.TaskList;

public class EventCommand implements Command {
    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private EventCommand(String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Command make(HashMap<String, String> args) throws AirisException {
        String description = args.get("main");
        if (description == null || description.isEmpty()) {
            throw new AirisException("Description cannot be empty");
        }

        String startStr = args.get("/from");
        if (startStr == null || startStr.isEmpty()) {
            throw new AirisException("Due date cannot be empty");
        }

        String endStr = args.get("/to");
        if (endStr == null || endStr.isEmpty()) {
            throw new AirisException("Due date cannot be empty");
        }

        LocalDateTime startTime, endTime;
        try {
            startTime = LocalDateTime.parse(startStr, all);
        } catch (DateTimeParseException e) {
            throw new AirisException("Cannot parse start date");
        }
        try {
            endTime = LocalDateTime.parse(endStr, all);
        } catch (DateTimeParseException e) {
            throw new AirisException("Cannot parse end date");
        }

        return new EventCommand(description, startTime, endTime);
    }

    @Override
    public Response process(Storage storage, TaskList tasklist) {
        Event task = new Event(this.description, this.startTime, this.endTime);
        tasklist.add(task);
        return Response.fromMessage(
                "I have added this task to your list:\n\t" + task
        );
    }
}
