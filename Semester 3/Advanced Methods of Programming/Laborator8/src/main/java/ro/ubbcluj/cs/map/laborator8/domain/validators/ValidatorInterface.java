package ro.ubbcluj.cs.map.laborator8.domain.validators;

public interface ValidatorInterface<T> {
    void validate(T entity) throws ValidatorException;
}