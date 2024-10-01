package ro.ubbcluj.cs.map.laborator7.domain.validators;

public interface ValidatorInterface<T> {
    void validate(T entity) throws ValidatorException;
}