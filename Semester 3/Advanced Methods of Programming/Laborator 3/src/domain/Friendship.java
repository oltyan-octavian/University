package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Friendship extends Entity<Tuple<Long,Long>> {

    private User u1;
    private User u2;
    LocalDateTime date;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");

    public Friendship(User u1, User u2) {
        this.u1=u1;
        this.u2=u2;
        Tuple<Long,Long> myTuple= new Tuple<>(u1.getId(),u2.getId());
        this.setId(myTuple);
        date = LocalDateTime.now();
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

    @Override
    public String toString() {
        return "Date: " + date.format(formatter) + ", IDs of friends: " + id;
    }
}
