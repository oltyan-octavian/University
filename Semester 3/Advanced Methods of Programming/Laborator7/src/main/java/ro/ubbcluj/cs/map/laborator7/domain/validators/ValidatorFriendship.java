package ro.ubbcluj.cs.map.laborator7.domain.validators;

import ro.ubbcluj.cs.map.laborator7.domain.Friendship;

import java.util.Objects;

public class ValidatorFriendship implements ValidatorInterface<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidatorException {
        if(Objects.equals(entity.getUser1().getId(), entity.getUser2().getId()))
            throw new ValidatorException("You cannot add yourself as a friend");
    }
}