package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * Represents a notification within the application.
 */
public class Notification {


    // properties
    private String message;
    private CustomLocalDate date;
    private CustomLocalTime time;


    private Type type;

    /**
     * Constructor for the class.
     * @param message The notification message.
     * @param time The time the notification was generated.
     */

    public Notification(){}

    public Notification(String message, LocalDateTime time, Type type){
        this.message = message;
        this.date = new CustomLocalDate(time.getYear(),time.getMonth().getValue(),time.getDayOfMonth());
        this.time = new CustomLocalTime(time.getHour(),time.getMinute(),time.getSecond());
        this.type = type;
    }


    public String toString () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.of(date.getLocalDate(),time.getLocalTime()).format(formatter) + "          " +  this.getMessage() ;
    }

    /**
     * Getters and setters.
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public CustomLocalDate getDate() {
        return date;
    }

    public void setDate(CustomLocalDate date) {
        this.date = date;
    }

    public CustomLocalTime getTime() {
        return time;
    }

    public void setTime(CustomLocalTime time) {
        this.time = time;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    /**
     * Enum to represent the type of notification.
     */
    public enum Type{
        //Todo.. SORT OUT COLOURS
        ADD("#06962f"),
        REMOVE("#fa0223"),
        EDIT("#fa8a02"),
        REVISION("#0e09b0");

        private String color;

        private Type(String color){
            this.color = color;
        }

        /**
         * Converts the type to it's color
         * @return String equivalent of the flight method..
         */
        public String getColor(){
            return this.color;
        }
    }
}


