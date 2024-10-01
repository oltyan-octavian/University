package ro.ubbcluj.cs.map.laborator7.repository;

import ro.ubbcluj.cs.map.laborator7.domain.validators.ValidatorInterface;
import ro.ubbcluj.cs.map.laborator7.domain.User;

import java.util.List;

public class UtilizatorFileRepository extends AbstractFileRepository<Long, User> {

    public UtilizatorFileRepository(String fileName, ValidatorInterface<User> validator) {
        super(fileName, validator);
    }

    @Override
    public User extractEntity(List<String> attributes) {
        //TODO: implement method
        User user = new User(attributes.get(1),attributes.get(2));
        user.setId(Long.parseLong(attributes.get(0)));

        return user;
    }

    @Override
    protected String createEntityAsString(User entity) {
        return entity.getId()+";"+entity.getFirstName()+";"+entity.getLastName();
    }
}
