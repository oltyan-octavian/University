package ro.ubbcluj.cs.map.service;


import ro.ubbcluj.cs.map.domain.Friendship;
import ro.ubbcluj.cs.map.domain.User;

public interface ServiceInterface< ID > {
    boolean addUser(User User);
    boolean deleteUser(ID id);

    Iterable<User> getAllUsers();

    boolean addFriendship(ID id1, ID id2);
    boolean deleteFriendship(ID id1, ID id2);

    Iterable<Friendship> getAllFriendships();
}
