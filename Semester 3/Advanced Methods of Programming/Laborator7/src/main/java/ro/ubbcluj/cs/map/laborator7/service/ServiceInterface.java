package ro.ubbcluj.cs.map.laborator7.service;


import ro.ubbcluj.cs.map.laborator7.domain.Friendship;
import ro.ubbcluj.cs.map.laborator7.domain.User;

public interface ServiceInterface< ID > {
    boolean addUser(User User);
    boolean deleteUser(ID id);

    Iterable<User> getAllUsers();

    boolean addFriendship(ID id1, ID id2);
    boolean deleteFriendship(ID id1, ID id2);

    Iterable<Friendship> getAllFriendships();
}
