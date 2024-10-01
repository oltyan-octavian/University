package ro.ubbcluj.cs.map.domain.validators;

public interface ValidatorInterface<T> {
    void validate(T entity) throws ValidatorException;
}