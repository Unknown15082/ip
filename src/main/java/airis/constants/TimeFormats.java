package airis.constants;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Different available time formats.
 */
public final class TimeFormats {
    public static final DateTimeFormatter STANDARD = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter SIMPLE = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static final DateTimeFormatter ALL = new DateTimeFormatterBuilder()
            .appendOptional(STANDARD)
            .appendOptional(SIMPLE)
            .appendOptional(ISO)
            .toFormatter();
}
