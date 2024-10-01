package ro.ubbcluj.cs.map.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Friendship extends Entity<Tuple<Long,Long>> {

    private User u1;
    private User u2;
    LocalDateTime date;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");

    public Friendship(User u1, User u2, LocalDateTime date) {
        this.u1=u1;
        this.u2=u2;
        this.date = date;
        Tuple<Long,Long> myTuple= new Tuple<>(u1.getId(),u2.getId());
        this.setId(myTuple);
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
