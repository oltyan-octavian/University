package service;

import domain.Friendship;
import domain.Tuple;
import domain.User;
import repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Service<ID> implements ServiceInterface<ID>{
    private Long ids = 100L;
    private Repository userRepository;
    private Repository friendshipRepository;

    public Service(Repository userRepository, Repository friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }
    @Override
    public boolean addUser(User User) {
        ids++;
        User.setId(ids);
        if(userRepository.save(User).isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(ID id) {
        User user = getUser(id);
        if(user != null) {
            Iterable<User> vector = getFriendshipsOfUser(id);
            if (vector != null) {
                vector.forEach(us -> {
                    Tuple<ID, Long> myT = new Tuple<>(id, us.getId());
                    Tuple<Long, ID> myT2 = new Tuple<>(us.getId(), id);
                    friendshipRepository.delete(myT);
                    friendshipRepository.delete(myT2);
                    us.removeFriend(user);
                });
            }
            if (userRepository.delete(id).isPresent()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean addFriendship(ID id1, ID id2) {
        User user1 = getUser(id1);
        User user2 = getUser(id2);
        Friendship pr = new Friendship(user1, user2, LocalDateTime.now());
        if (friendshipRepository.save(pr).isEmpty()) {
            user1.addFriend(user2);
            user2.addFriend(user1);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFriendship(ID id1, ID id2) {
        User user1 = getUser(id1);
        User user2 = getUser(id2);
        Tuple<ID, ID> myT = new Tuple<>(id1, id2);
        if (friendshipRepository.delete(myT).isPresent()) {
            user1.removeFriend(user2);
            user2.removeFriend(user1);
            return true;
        }
        return false;
    }

    @Override
    public Iterable<User> getAllFriendships() {
        return friendshipRepository.findAll();
    }

    public User getUser(ID id) {
        for (var it : getAllUsers()) {
            if (it.getId().equals(id)) {
                return it;
            }
        }
        return null;
    }

    public Iterable<User> getFriendshipsOfUser(ID id){
        User user = getUser(id);
        if(user.getFriends().isEmpty())
            return null;
        else return user.getFriends();
    }

    public List<User> DFS(User u, Set<User> set)
    {
        List<User> finallist = new ArrayList<>();
        finallist.addLast(u);
        set.add(u);
        u.getFriends().forEach(f -> {
            if (!set.contains(f))
            {
                List<User> l = DFS(f, set);
                l.forEach(finallist::add);
            }
        });
        return finallist;
    }

    public int Communities(){
        Iterable<User> all=getAllUsers();
        Set<User> set=new HashSet<>();
        AtomicInteger nr= new AtomicInteger();
        if (all!=null) {
            all.forEach(u -> {
                if (!set.contains(u)) {
                    nr.getAndIncrement();
                    DFS(u, set);
                }
            });
        }
        return nr.get();
    }

    public List<User> Sociable(){
        AtomicReference<List<User>> most= new AtomicReference<>(new ArrayList<>());
        Iterable<User> all = getAllUsers();
        Set<User> set=new HashSet<>();
        var ref = new Object() {
            int max = -1;
        };
        if (all!=null) {
            all.forEach(u -> {
                if (!set.contains(u)) {
                    List<User> list = DFS(u, set);
                    if (list.size() > ref.max) {
                        most.set(list);
                        ref.max = list.size();
                    }
                }
            });
        }

        return most.get();
    }

}
