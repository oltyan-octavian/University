package ro.ubbcluj.cs.map.repository;

import ro.ubbcluj.cs.map.domain.User;
import ro.ubbcluj.cs.map.domain.validators.ValidatorInterface;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserDBRepository implements Repository<Long, User> {

    private String url;
    private String username;
    private String password;

    private ValidatorInterface<User> validator;


    public UserDBRepository(String url, String username, String password, ValidatorInterface<User> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator= validator;
    }

    @Override
    public Optional<User> findOne(Long longID) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from users " +
                     "where id = ?");

        ) {
            statement.setInt(1, Math.toIntExact(longID));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                User u = new User(firstName, lastName);
                u.setId(longID);
                return Optional.ofNullable(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from users");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                User user = new User(firstName, lastName);
                user.setId(id);
                users.add(user);

            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> save(User entity) {

        validator.validate(entity);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("insert into users(id, first_name,last_name) VALUES(?, ?,?)")
        ){
            statement.setLong(1, entity.getId());
            statement.setString(2,entity.getFirstName());
            statement.setString(3,entity.getLastName());
            statement.executeUpdate();
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }

        return Optional.empty() ;
    }

    @Override
    public Optional<User> delete(Long aLong) {
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("delete from users where( id = ? ) ")
        ){
            statement.setLong(1,aLong);
            Optional<User> user = findOne(aLong);
            if(user.isPresent()){
                if(statement.executeUpdate() > 0){
                    user.get().setId(-1L);
                    return user;
                }
                throw new RuntimeException("Delete failed");
            }
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User entity) {
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("update users set first_name=?,last_name=? where id=?")
        ) {
            statement.setString(1,entity.getFirstName());
            statement.setString(2,entity.getLastName());
            statement.setLong(3,entity.getId());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
