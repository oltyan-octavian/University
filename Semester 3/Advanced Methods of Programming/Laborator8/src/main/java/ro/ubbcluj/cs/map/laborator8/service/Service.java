package ro.ubbcluj.cs.map.laborator8.service;

import javafx.scene.control.Alert;
import ro.ubbcluj.cs.map.laborator8.controller.MessageAlert;
import ro.ubbcluj.cs.map.laborator8.domain.Friendship;
import ro.ubbcluj.cs.map.laborator8.domain.Message;
import ro.ubbcluj.cs.map.laborator8.domain.Tuple;
import ro.ubbcluj.cs.map.laborator8.domain.User;
import ro.ubbcluj.cs.map.laborator8.repository.Repository;
import ro.ubbcluj.cs.map.laborator8.repository.paging.Page;
import ro.ubbcluj.cs.map.laborator8.repository.paging.Pageable;
import ro.ubbcluj.cs.map.laborator8.repository.paging.PagingRepository;
import ro.ubbcluj.cs.map.laborator8.utils.events.ChangeEvent;
import ro.ubbcluj.cs.map.laborator8.utils.events.ChangeEventType;
import ro.ubbcluj.cs.map.laborator8.utils.observer.Observable;
import ro.ubbcluj.cs.map.laborator8.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.codec.digest.DigestUtils;

public class Service<ID> implements ServiceInterface<ID>, Observable<ChangeEvent> {
    private PagingRepository userRepository;
    private Repository<Tuple<Long,Long>, Friendship> friendshipRepository;
    private Repository messageRepository;
    private List<Observer<ChangeEvent>> observers=new ArrayList<>();

    public Service(PagingRepository userRepository, Repository friendshipRepository, Repository messageRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
        this.messageRepository = messageRepository;
    }
    @Override
    public boolean addUser(User User) {
        if(userRepository.save(User).isEmpty()){
            notifyObservers(new ChangeEvent(ChangeEventType.ADDUSER, User));
            return true;
        }
        return false;
    }

    public boolean updateUser(ID id, String firstName, String lastName){
        User us = new User(firstName, lastName);
        us.setId((Long) id);
        if(userRepository.update(us).isEmpty()){
            notifyObservers(new ChangeEvent(ChangeEventType.UPDATEUSER, us));
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(ID id) {
        User user = getUser(id);
            if (userRepository.delete(id).isPresent()) {
                notifyObservers(new ChangeEvent(ChangeEventType.DELETEUSER, user));
                return true;
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
            notifyObservers(new ChangeEvent(ChangeEventType.ADDFRIENDSHIP, pr));
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFriendship(ID id1, ID id2) {
        User user1 = getUser(id1);
        User user2 = getUser(id2);
        Tuple<ID, ID> myT = new Tuple<>(id1, id2);
        Optional<Friendship> pr = friendshipRepository.delete((Tuple<Long, Long>) myT);
        if (pr.isPresent()) {
            user1.removeFriend(user2);
            user2.removeFriend(user1);
            notifyObservers(new ChangeEvent(ChangeEventType.DELETEFRIENDSHIP, pr.get()));
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

    public List<User> getFriendshipsOfUser(ID id){
        User user = getUser(id);
        List<User> friends = new ArrayList<>();
        Iterable<Friendship> all = getAllFriendships();
        List<Friendship> friendships = StreamSupport.stream(all.spliterator(), false)
                .filter(p -> (p.getUser1().getId().equals(id) || p.getUser2().getId().equals(id)))
                .collect(Collectors.toList());

        friendships.forEach(f -> {
            if(f.getUser1().getId().equals(id)){
                friends.add(f.getUser2());
            }
            if(f.getUser2().getId().equals(id)){
                friends.add(f.getUser1());
            }
        });

        return friends;
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

    public boolean acceptFriendshipRequest(Friendship fr){
        if(!Objects.equals(fr.getStatus(), "pending")) {
            return false;
        }
        else {
            fr.acceptFriendship();
            if(friendshipRepository.update(fr).isEmpty()){
                notifyObservers(new ChangeEvent(ChangeEventType.ADDFRIENDSHIP, fr));
                return true;
            }
        }
        return false;
    }
    public boolean rejectFriendshipRequest(Friendship fr){
        if(!Objects.equals(fr.getStatus(), "pending")) {
            return false;
        }
        else {
            fr.rejectFriendship();
            if(friendshipRepository.update(fr).isEmpty()){
                notifyObservers(new ChangeEvent(ChangeEventType.ADDFRIENDSHIP, fr));
                return true;
            }
        }
        return false;
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

    public boolean addMessage(Message msg) {
        Optional<Message> myMessage = messageRepository.save(msg);
        notifyObservers(new ChangeEvent(ChangeEventType.ADDMESSAGE, null));
        return myMessage.isEmpty();
    }

    public ArrayList<Message> findMessages(Long id1, Long id2) {
        Iterable<Message> messages = messageRepository.findAll();
        ArrayList<Message> returnMessages = new ArrayList<>();
        for (var a : messages) {
            if (a.getFrom().getId().equals(id1)) {
                for (User utilizator : a.getTo()) {
                    if (utilizator.getId().equals(id2)) {
                        returnMessages.add(a);
                    }
                }
            }
            if (a.getFrom().getId().equals(id2)) {
                for (User utilizator : a.getTo()) {
                    if (utilizator.getId().equals(id1)) {
                        returnMessages.add(a);
                    }
                }
            }
        }
        returnMessages.sort(Comparator.comparing(Message::getDate));
        return returnMessages;
    }

    public Message FindByID(Long id){
        Iterable<Message> allMessages = messageRepository.findAll();
        return StreamSupport.stream(allMessages.spliterator(), false)
                .filter(msg -> Objects.equals(msg.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public String encode(String password) {
        return DigestUtils.sha256Hex(password);
    }

    public User connect(String email,String password){
        Iterable<User> allusers=userRepository.findAll();
        password=encode(password);
        for (User us:allusers){
            if (Objects.equals(us.getEmail(), email) && Objects.equals(us.getPassword(), password))
            {
                return us;
            }
        }
        return null;
    }

    @Override
    public void addObserver(Observer<ChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<ChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(ChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public Page<User> getUsersOnPage(Pageable pageable){
        return userRepository.findAll(pageable);
    }
}
