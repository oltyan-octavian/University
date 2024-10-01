package Task;

import Task.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageTask extends Task {

    private String message;
    private String from;
    private String to;
    private LocalDateTime date;


    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm");

    public MessageTask(String i, String d, String message, String from, String to, LocalDateTime date) {
        super(i,d);
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    @Override
    public void execute(){
        System.out.println(date.format(DATA_FORMATTER) + ":" + message);
    }



    @Override
    public String toString() {
        return "Task.MessageTask{" +
                "id='" + getId() + '\'' +
                ", descriere='" + getDescription() + '\'' +
                "message='" + message + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date=" + date.format(DATA_FORMATTER) +
                '}';
    }
}