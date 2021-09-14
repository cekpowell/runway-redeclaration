package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a notification within the application.
 */
public class Notification {

    // properties
    private String message;
    private LocalDateTime time;

    /**
     * Constructor for the class.
     * @param message The notification message.
     * @param time The time the notification was generated.
     */
    public Notification(String message, LocalDateTime time){
        this.message = message;
        this.time = time;
    }

    /**
     * Converts the notification to a string.
     * @param message The notification message.
     */
    public String toString (String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(formatter) + "          " +  message ;
    }

    /**
     * Getters and setters.
     */

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
