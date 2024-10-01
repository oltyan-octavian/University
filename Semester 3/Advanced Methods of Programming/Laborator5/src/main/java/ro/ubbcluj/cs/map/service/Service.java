package ro.ubbcluj.cs.map.service;

import ro.ubbcluj.cs.map.domain.Friendship;
import ro.ubbcluj.cs.map.domain.Tuple;
import ro.ubbcluj.cs.map.domain.User;
import ro.ubbcluj.cs.map.repository.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service<ID> implements ServiceInterface<ID> {
    private Long ids = 100L;
    private Repository userRepository;
    private Repository<Tuple<Long,Long>, Friendship> friendshipRepository;

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
                    friendshipRepository.delete((Tuple<Long, Long>) myT);
                    friendshipRepository.delete((Tuple<Long, Long>) myT2);
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
        if (friendshipRepository.delete((Tuple<Long, Long>) myT).isPresent()) {
            user1.removeFriend(user2);
            user2.removeFriend(user1);
            return true;
        }
        return false;
    }

    @Override
    public Iterable<Friendship> getAllFriendships() {
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
        finallist.add(u);
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

    public List<Friendship> FriendshipMonth(ID id, String month) {
        Iterable<Friendship> all = getAllFriendships();
        if (all == null) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(all.spliterator(), false)
                .filter(p -> (p.getUser1().getId().equals(id) || p.getUser2().getId().equals(id)) &&
                        p.getDate().getMonth().toString().equals(month))
                .collect(Collectors.toList());
    }

}
