package ro.ubbcluj.cs.map.laborator8.utils.events;


import ro.ubbcluj.cs.map.laborator8.domain.Entity;
import ro.ubbcluj.cs.map.laborator8.domain.Friendship;
import ro.ubbcluj.cs.map.laborator8.domain.User;

public class ChangeEvent implements Event {
    private ChangeEventType type;
    private User userdata, userOldData;
    private Friendship friendshipdata, friendshipOldData;

    public ChangeEvent(ChangeEventType type, Entity data) {
        this.type = type;
        if (type == ChangeEventType.ADDUSER || type == ChangeEventType.DELETEUSER)
            this.userdata = (User) data;
        if (type == ChangeEventType.ADDFRIENDSHIP || type == ChangeEventType.DELETEFRIENDSHIP)
            this.friendshipdata = (Friendship) data;
    }
    public ChangeEvent(ChangeEventType type, Entity data, Entity oldData) {
        this.type = type;
        if (type == ChangeEventType.ADDUSER || type == ChangeEventType.DELETEUSER)
        {
            this.userdata = (User) data;
            this.userOldData = (User) oldData;
        }
        if (type == ChangeEventType.ADDFRIENDSHIP || type == ChangeEventType.DELETEFRIENDSHIP)
        {
            this.friendshipdata = (Friendship) data;
            this.friendshipOldData = (Friendship) oldData;
        }
    }

    public ChangeEventType getType() {
        return type;
    }

    public User getUserdata() {
        return userdata;
    }

    public User getUserOldData() {
        return userOldData;
    }

    public Friendship getFriendshipdata() {
        return friendshipdata;
    }

    public Friendship getFriendshipOldData() {
        return friendshipOldData;
    }
}