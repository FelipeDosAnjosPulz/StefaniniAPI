package stefanini.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtil {

    public static LocalDateTime dataAtual() {
        return LocalDateTime.now();
    }

    public static String LocalDateTimeToString(LocalDateTime data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return data.format(formatter);
    }

}
