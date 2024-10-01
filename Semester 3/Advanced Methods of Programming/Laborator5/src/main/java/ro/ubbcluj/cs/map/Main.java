package ro.ubbcluj.cs.map;

import ro.ubbcluj.cs.map.domain.Friendship;
import ro.ubbcluj.cs.map.domain.Tuple;
import ro.ubbcluj.cs.map.domain.User;
import ro.ubbcluj.cs.map.domain.validators.ValidatorFriendship;
import ro.ubbcluj.cs.map.domain.validators.ValidatorUser;
import ro.ubbcluj.cs.map.repository.Repository;
import ro.ubbcluj.cs.map.repository.UserDBRepository;
import ro.ubbcluj.cs.map.service.Service;
import ro.ubbcluj.cs.map.ui.Console;

public class Main {

    public static void main(String[] args) {

        String url="jdbc:postgresql://localhost:5432/socialnetwork";
        String username = "postgres";
        String password = "12345678";
        /*User u1 = new User("Tudor", "Olar");
        u1.setId(1L);
        User u2 = new User("Octavian", "Oltyan");
        u2.setId(2L);
        User u3 = new User("Ionut", "Manea");
        u3.setId(3L);
        InMemoryRepository<Long, User> usersrepo=new InMemoryRepository<>(new ValidatorUser());
        InMemoryRepository<Tuple<Long,Long>, Friendship> friendshiprepo=new InMemoryRepository<>(new ValidatorFriendship());
        usersrepo.save(u1);
        usersrepo.save(u2);
        usersrepo.save(u3);*/
        Repository<Long, User> usersrepo = new UserDBRepository(url, username, password, new ValidatorUser());
        Repository<Tuple<Long,Long>, Friendship> friendshiprepo = new ro.ubbcluj.cs.map.repository.FriendshipDBRepository(url, username, password, new ValidatorFriendship());
        Service<Long> serv = new Service<>(usersrepo,friendshiprepo);
        Console cons = new Console(serv);
        cons.execute();
    }
}
