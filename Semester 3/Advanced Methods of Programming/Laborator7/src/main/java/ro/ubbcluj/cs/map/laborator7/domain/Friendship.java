package ro.ubbcluj.cs.map.laborator7.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Friendship extends Entity<Tuple<Long,Long>> {

    User u1;
    Long id1;
    User u2;
    Long id2;
    LocalDateTime date;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");

    public Friendship(User u1, User u2, LocalDateTime date) {
        this.u1=u1;
        this.id1 = u1.getId();
        this.id2 = u2.getId();
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

    public Long getId1() {
        return id1;
    }

    public Long getId2() {
        return id2;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Date: " + date.format(formatter) + ", IDs of friends: " + id;
    }
}
