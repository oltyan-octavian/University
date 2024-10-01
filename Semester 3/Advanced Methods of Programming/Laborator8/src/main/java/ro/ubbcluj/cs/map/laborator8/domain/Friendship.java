package ro.ubbcluj.cs.map.laborator8.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Friendship extends Entity<Tuple<Long,Long>> {

    User u1;
    User u2;
    LocalDateTime date;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");
    Long sender;
    String status;

    public Friendship(User u1, User u2, LocalDateTime date) {
        this.u1=u1;
        this.u2=u2;
        this.date = date;
        Tuple<Long,Long> myTuple= new Tuple<>(u1.getId(),u2.getId());
        this.setId(myTuple);
        this.status = "pending";
    }

    public Friendship(User u1, User u2, LocalDateTime date, String status) {
        this.u1=u1;
        this.u2=u2;
        this.date = date;
        Tuple<Long,Long> myTuple= new Tuple<>(u1.getId(),u2.getId());
        this.setId(myTuple);
        this.status = status;
    }

    public User getUser1() {
        return u1;
    }

    public void setUser1(User user1) {
        this.u1 = user1;
    }

    public User getUser2() {
        return u2;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }
    public Long getUser2ID(){
        return u2.getId();
    }
    public String getUser2firstName(){
        return u2.getFirstName();
    }

    public String getUser2lastName(){
        return u2.getLastName();
    }
    public void swapUsers(){
        User a = u1;
        u1 = u2;
        u2 = a;
    }

    public String getStatus() {
        return status;
    }

    public void acceptFriendship(){
        this.status = "accepted";
    }
    public void rejectFriendship(){
        this.status = "rejected";
    }
    public void setUser2(User user2) {
        this.u2 = user2;
    }

    public LocalDateTime getDate() {
        return date;
    }


    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Date: " + date.format(formatter) + ", IDs of friends: " + id;
    }
}
