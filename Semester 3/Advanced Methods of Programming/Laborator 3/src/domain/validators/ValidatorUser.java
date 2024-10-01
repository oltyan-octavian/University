package domain.validators;

import domain.User;
import domain.validators.ValidatorException;
import domain.validators.ValidatorInterface;

public class ValidatorUser implements ValidatorInterface<User> {
    @Override
    public void validate(User entity) throws ValidatorException {
        for (char c: entity.getFirstName().toCharArray())
        {
            if (Character.isDigit(c) || Character.isWhitespace(c)){
                throw new ValidatorException("Primul nume nu este valid");
            }
        }
        for (char c: entity.getLastName().toCharArray()) {
            if (Character.isDigit(c) || Character.isWhitespace(c)) {
                throw new ValidatorException("Al doilea nume nu este valid");
            }
        }
    }
}

