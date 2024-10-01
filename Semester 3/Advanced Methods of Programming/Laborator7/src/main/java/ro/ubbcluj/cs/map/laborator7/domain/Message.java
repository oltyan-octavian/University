package ro.ubbcluj.cs.map.laborator7.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Message extends Entity<Long>{
    private String message;
    private User from;
    private List<User> to;
    private LocalDateTime date;
    private Message reply;

    public Message(String message, User from, List<User> to, LocalDateTime date) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
        this.reply = null;
    }

    public Message(String message, User from, List<User> to, LocalDateTime date, Message reply) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
        this.reply = reply;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public List<User> getTo() {
        return to;
    }

    public void setTo(List<User> to) {
        this.to = to;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Message getReply() {
        return reply;
    }

    public void setReply(Message reply) {
        this.reply = reply;
    }
}
