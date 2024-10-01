package ro.iss2024.laborator5iss.service;

public interface IService<ID>{
    boolean addUser(String name, String password, String PNC, String address, String phoneNumber);
}
