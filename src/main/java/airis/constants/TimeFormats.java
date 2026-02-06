package airis.constants;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public final class TimeFormats {
    public static final DateTimeFormatter standard = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter simple = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final DateTimeFormatter iso = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static final DateTimeFormatter all = new DateTimeFormatterBuilder()
            .appendOptional(standard)
            .appendOptional(simple)
            .appendOptional(iso)
            .toFormatter();
}
