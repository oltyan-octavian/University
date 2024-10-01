package ro.iss2024.laborator5iss.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.iss2024.laborator5iss.domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BookDBRepository implements IBookRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger(UserDBRepository.class);

    public BookDBRepository(Properties props){
        logger.info("Initializing BookDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Book save(Book entity) {
        logger.traceEntry("saving book {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("insert into books(title, author, publishingHouse, year, rented) values (?, ?, ?, ?, ?) ")){
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getAuthor());
            statement.setString(3, entity.getPublishingHouse());
            statement.setInt(4, entity.getYear());
            statement.setBoolean(5, entity.isRented());
            int result = statement.executeUpdate();
            return entity;
        }catch(SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return null;
    }

    @Override
    public Book delete(Integer aLong) {
        logger.traceEntry("deleting book with id {} ",aLong);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("delete from books where id = ?")){
            statement.setInt(1, aLong);
            int result = statement.executeUpdate();
            return null;
        }catch(SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return null;
    }

    @Override
    public Book update(Book entity) {
        logger.traceEntry("updating book {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("update books set rented=? where id = ?")){
            statement.setBoolean(1, entity.isRented());
            statement.setInt(2, entity.getId());
            int result = statement.executeUpdate();
            return entity;
        }catch(SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return null;
    }

    @Override
    public Book findOne(Integer aLong) {
        logger.traceEntry("finding book with id {} ",aLong);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("select * from books where id = ?")) {
            statement.setInt(1, aLong);
            try (var result = statement.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String title = result.getString("title");
                    String author = result.getString("author");
                    String publishingHouse = result.getString("publishingHouse");
                    int year = result.getInt("year");
                    boolean rented = result.getBoolean("rented");
                    Book book = new Book(id, title, author, publishingHouse, year, rented);
                    return book;
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return null;
    }

    @Override
    public Iterable<Book> findAll() {
        logger.traceEntry("finding all books");
        List<Book> books = new ArrayList<>();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("select * from books")){
            try(var result = statement.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    String title = result.getString("title");
                    String author = result.getString("author");
                    String publishingHouse = result.getString("publishingHouse");
                    int year = result.getInt("year");
                    boolean rented = result.getBoolean("rented");
                    Book book = new Book(id, title, author, publishingHouse, year, rented);
                    books.add(book);
                }
            }}
        catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return books;
    }

}
