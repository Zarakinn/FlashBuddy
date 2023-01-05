package tn.flashcards.model.stats;

import java.time.format.DateTimeFormatter;

public class DateFormat {
    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    public static DateTimeFormatter getDayFormater() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public static DateTimeFormatter getMinuteFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
    }
}
