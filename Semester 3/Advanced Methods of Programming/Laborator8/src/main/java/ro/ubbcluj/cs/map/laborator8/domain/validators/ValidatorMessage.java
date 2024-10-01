package ro.ubbcluj.cs.map.laborator8.domain.validators;
import ro.ubbcluj.cs.map.laborator8.domain.Message;
import ro.ubbcluj.cs.map.laborator8.domain.User;

public class ValidatorMessage implements ValidatorInterface<Message> {
    @Override
    public void validate(Message entity) throws ValidatorException {
        if (entity.getFrom().getId() < 0) {
            throw new ValidatorException("The user that sends the message doesn't exist");
        }
        for (User c : entity.getTo()) {
            if (c.getId() < 0) {
                throw new ValidatorException("One of the users that receive the message doesn't exist");
            }
        }
    }
}

