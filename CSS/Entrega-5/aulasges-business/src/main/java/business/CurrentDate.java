package business;

import java.time.LocalDateTime;

//mock class
public class CurrentDate {

    public static LocalDateTime getCurrentDate() {
        return LocalDateTime.of(2020, 4, 20, 14, 0);
    }

}
