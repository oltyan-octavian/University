package service;

import domain.Tuple;
import domain.User;
import domain.Friendship;
import repository.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if(userRepository.save(User) == null){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(ID id) {
        User user = getUser(id);
        if (user != null)
        {
            Iterable<User> vector = getFriendshipsOfUser(id);
            if(vector != null) {
                for (User us : vector) {
                    Tuple<ID, Long> myT = new Tuple<>(id, us.getId());
                    Tuple<Long, ID> myT2 = new Tuple<>(us.getId(), id);
                    friendshipRepository.delete(myT);
                    friendshipRepository.delete(myT2);
                    us.removeFriend(user);
                }
            }
            if (userRepository.delete(id) == null) {
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
        Friendship pr = new Friendship(user1, user2);
        if (friendshipRepository.save(pr) == null) {
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
        if (friendshipRepository.delete(myT) != null) {
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

        for (User f:u.getFriends())
        {
            if (!set.contains(f))
            {
                List<User> l = DFS(f, set);
                for(User x : l)
                    finallist.add(x);
            }
        }
        return finallist;
    }

    public int Communities(){
        Iterable<User> all=getAllUsers();
        Set<User> set=new HashSet<>();
        int nr=0;
        if (all!=null) {
            for (User u : all) {
                if (!set.contains(u)) {
                    nr++;
                    DFS(u, set);
                }
            }
        }
        return nr;
    }

    public List<User> Sociable(){
        List<User> most=new ArrayList<>();
        Iterable<User> all = getAllUsers();
        Set<User> set=new HashSet<>();
        int max=-1;
        if (all!=null) {
            for (User u : all) {
                if (!set.contains(u)) {
                    List<User> list = DFS(u, set);
                    if (list.size() > max) {
                        most = list;
                        max = list.size();
                    }
                }
            }
        }

        return most;
    }

}
