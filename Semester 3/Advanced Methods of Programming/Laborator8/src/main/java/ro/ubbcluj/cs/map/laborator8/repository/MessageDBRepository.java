package ro.ubbcluj.cs.map.laborator8.repository;

import ro.ubbcluj.cs.map.laborator8.domain.validators.ValidatorInterface;
import ro.ubbcluj.cs.map.laborator8.domain.Entity;
import ro.ubbcluj.cs.map.laborator8.domain.Message;
import ro.ubbcluj.cs.map.laborator8.domain.User;
import ro.ubbcluj.cs.map.laborator8.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class MessageDBRepository implements Repository<Long, Message> {

    private String url;
    private String username;
    private String password;
    private ValidatorInterface<Message> validator;

    public MessageDBRepository(String url, String username, String password, ValidatorInterface<Message> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Message> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Message> findAll() {
        String findStatement = "select * from messages";
        Set<Message> messages = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(findStatement);
             ResultSet resultSet = preparedStatement.executeQuery();

             PreparedStatement statement1 = connection.prepareStatement("select * from users where id=?");
        ) {
            ResultSet resultSet2;
            while (resultSet.next()) {
                Long id = (long) resultSet.getInt("id");
                String mesajul = resultSet.getString("message");
                Long idUt = resultSet.getLong("from");
                Long idReply = (long) resultSet.getInt("reply");
                statement1.setLong(1, idUt);
                resultSet2 = statement1.executeQuery();
                resultSet2.next();
                User utilizator = new User(resultSet2.getString("first_name"), resultSet2.getString("last_name"));
                utilizator.setId(idUt);

                Timestamp timestamp = resultSet.getTimestamp("date");
                LocalDateTime date = timestamp.toLocalDateTime();

                Array array = resultSet.getArray("to");
                Long[] idArray = (Long[]) array.getArray();
                List<Long> idList = new ArrayList<>(List.of(idArray));

                List<User> lastList = new ArrayList<User>();
                for (var a : idList) {
                    statement1.setLong(1, a);
                    resultSet2 = statement1.executeQuery();
                    resultSet2.next();
                    User u = new User(resultSet2.getString("first_name"), resultSet2.getString("last_name"));
                    u.setId(a);
                    lastList.add(u);
                }

                Message message = new Message(mesajul, utilizator, lastList, date);
                message.setId(id);
                message.setReply(idReply);
                messages.add(message);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return messages;
    }

        @Override
        public Optional<Message> save(Message entity) {
            String insertSqlStatement = "insert INTO messages (message, \"from\", \"to\", date, reply)" +
                    "VALUES (?, ?, ?, ?, ?)";
            validator.validate(entity);
            try (Connection connection = DriverManager.getConnection(url, username, password);

                 PreparedStatement statement = connection.prepareStatement(insertSqlStatement);

            ) {
                statement.setString(1, entity.getMessage());
                statement.setLong(2, entity.getFrom().getId());
                Array array = connection.createArrayOf("BIGINT", entity.getTo().stream().map(User::getId).toArray());
                statement.setArray(3, array);
                Timestamp timestamp = Timestamp.valueOf(entity.getDate());
                statement.setTimestamp(4, timestamp);
                Long reply = entity.getReply() != 0L ? entity.getReply() : 0L;
                statement.setObject(5, reply);

                statement.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


            return Optional.empty();
        }

    @Override
    public Optional<Message> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Message> update(Message entity) {
        return Optional.empty();
    }
}
