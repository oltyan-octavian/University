import domain.Friendship;
import domain.Tuple;
import domain.User;
import domain.validators.ValidatorFriendship;
import domain.validators.ValidatorUser;
import repository.InMemoryRepository;
import service.Service;
import ui.Console;

public class Main {

    public static void main(String[] args) {

        User u1 = new User("Tudor", "Olar");
        u1.setId(1L);
        User u2 = new User("Octavian", "Oltyan");
        u2.setId(2L);
        User u3 = new User("Ionut", "Manea");
        u3.setId(3L);
        InMemoryRepository<Long, User> usersrepo=new InMemoryRepository<>(new ValidatorUser());
        InMemoryRepository<Tuple<Long,Long>, Friendship> friendshiprepo=new InMemoryRepository<>(new ValidatorFriendship());
        usersrepo.save(u1);
        usersrepo.save(u2);
        usersrepo.save(u3);
        Service<Long> serv = new Service<>(usersrepo,friendshiprepo);
        Console cons = new Console(serv);
        cons.execute();
    }
}
