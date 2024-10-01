package ro.ubbcluj.cs.map.laborator7.repository;

import ro.ubbcluj.cs.map.laborator7.domain.validators.ValidatorInterface;
import ro.ubbcluj.cs.map.laborator7.domain.Friendship;
import ro.ubbcluj.cs.map.laborator7.domain.Tuple;
import ro.ubbcluj.cs.map.laborator7.domain.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class  FriendshipDBRepository implements Repository<Tuple<Long, Long>, Friendship> {


    private String url;
    private String username;
    private String password;
    private ValidatorInterface<Friendship> validator;

    public FriendshipDBRepository(String url, String username, String password, ValidatorInterface<Friendship> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }


    @Override
    public Optional<Friendship> findOne(Tuple<Long, Long> longLongTuple) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT u1.first_name AS u1_first_name, " +
                     "u1.last_name AS u1_last_name, " +
                     "u2.first_name AS u2_first_name, " +
                     "u2.last_name AS u2_last_name, " +
                     "f.date AS date, " +
                     "id1 as id1, " +
                     "id2 as id2 FROM friendships f JOIN users u1 ON f.id1 = u1.id JOIN users u2 ON f.id2 = u2.id " +
                     "where ( id1 = ? and id2 = ?) or (id2 = ? and id1 = ?)");
        ) {
            statement.setLong(1, longLongTuple.getLeft());
            statement.setLong(2, longLongTuple.getRight());
            statement.setLong(3, longLongTuple.getLeft());
            statement.setLong(4, longLongTuple.getRight());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String u1firstName = resultSet.getString("u1_first_name");
                String u1lastName = resultSet.getString("u1_last_name");
                String u2firstName = resultSet.getString("u2_first_name");
                String u2lastName = resultSet.getString("u2_last_name");
                Timestamp timestamp = resultSet.getTimestamp("date");

                Long id1 = longLongTuple.getLeft();
                Long id2 = longLongTuple.getRight();

                LocalDateTime MyDate = timestamp.toLocalDateTime();

                User u1 = new User(u1firstName, u1lastName);
                u1.setId(id1);
                User u2 = new User(u2firstName, u2lastName);
                u2.setId(id2);
                Tuple<Long, Long> id = new Tuple<>(id1, id2);

                Friendship prietenie = new Friendship(u1, u2, MyDate);
                prietenie.setId(id);
                return Optional.of(prietenie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT u1.first_name AS u1_first_name, u1.last_name AS u1_last_name, u2.first_name AS u2_first_name, u2.last_name AS u2_last_name, f.date AS date, id1 as id1, id2 as id2 FROM friendships f JOIN users u1 ON f.id1 = u1.id JOIN users u2 ON f.id2 = u2.id" );
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String u1firstName = resultSet.getString("u1_first_name");
                String u1lastName = resultSet.getString("u1_last_name");
                String u2firstName = resultSet.getString("u2_first_name");
                String u2lastName = resultSet.getString("u2_last_name");
                Timestamp timestamp = resultSet.getTimestamp("date");

                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");

                LocalDateTime MyDate = timestamp.toLocalDateTime();

                User u1 = new User(u1firstName, u1lastName);
                u1.setId(id1);
                User u2 = new User(u2firstName, u2lastName);
                u2.setId(id2);
                Tuple<Long, Long> id = new Tuple<>(id1, id2);

                Friendship prietenie = new Friendship(u1, u2, MyDate);
                prietenie.setId(id);
                friendships.add(prietenie);
            }
            return friendships;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> save(Friendship entity) {
        validator.validate(entity);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("insert into friendships(id1,id2,date) VALUES(?,?,?)")
        ){
            statement.setLong(1,entity.getUser1().getId());
            statement.setLong(2,entity.getUser2().getId());
            LocalDateTime Mydate = entity.getDate();
            Timestamp timestamp = Timestamp.valueOf(Mydate);
            statement.setTimestamp(3,timestamp);
            statement.executeUpdate();
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }

        return Optional.empty() ;
    }

    @Override
    public Optional<Friendship> delete(Tuple<Long, Long> longLongTuple) {
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("delete from friendships where( id1 = ? and id2 = ?) or (id2 = ? and id1 = ?) ")
        ){
            statement.setLong(1,longLongTuple.getLeft());
            statement.setLong(2,longLongTuple.getRight());
            statement.setLong(3,longLongTuple.getLeft());
            statement.setLong(4,longLongTuple.getRight());
            Optional<Friendship> friendship = findOne(longLongTuple);
            if(friendship.isPresent()){
                if(statement.executeUpdate() > 0){
                    friendship.get().setId(new Tuple<>(-1L, -1L));
                    return friendship;
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
    public Optional<Friendship> update(Friendship entity) {
        try(Connection connection = DriverManager.getConnection(url,username,password);
        PreparedStatement statement = connection.prepareStatement("update friendships set date=? where (id1=? and id2 = ?) or(id2 = ? and id1 = ?)")
        ) {
            LocalDateTime myDate = entity.getDate();
            Timestamp timestamp = Timestamp.valueOf(myDate);
            statement.setTimestamp(1,timestamp);
            statement.setLong(2,entity.getId().getLeft());
            statement.setLong(3,entity.getId().getRight());
            statement.setLong(4,entity.getId().getLeft());
            statement.setLong(5,entity.getId().getRight());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
