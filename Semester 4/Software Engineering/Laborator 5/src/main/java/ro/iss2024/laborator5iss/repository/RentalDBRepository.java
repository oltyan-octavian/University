package ro.iss2024.laborator5iss.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.iss2024.laborator5iss.domain.Rental;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RentalDBRepository implements IRentalRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger(UserDBRepository.class);

    public RentalDBRepository(Properties props){
        logger.info("Initializing RentalDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Rental save(Rental entity) {
        logger.traceEntry("saving rental {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("insert into rentals(userID, bookID, rentalDate, returnDate) values (?, ?, ?, ?) ")){
            statement.setInt(1, entity.getUserID());
            statement.setInt(2, entity.getBookID());
            statement.setDate(3, entity.getRentalDate());
            statement.setDate(4, entity.getReturnDate());
            int result = statement.executeUpdate();
            return entity;
        }catch(SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return null;
    }

    @Override
    public Rental delete(Integer aLong) {
        return null;
    }

    @Override
    public Rental update(Rental entity) {
        logger.traceEntry("updating rental {} ", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement statement = con.prepareStatement("update rentals set returnDate = ? where id = ?")) {
            statement.setDate(1, entity.getReturnDate());
            statement.setInt(2, entity.getId());
            int result = statement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public Rental findOne(Integer aLong) {
        logger.traceEntry("finding rental with id {} ",aLong);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("select * from rentals where id = ?")) {
            statement.setInt(1, aLong);
            try (var result = statement.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    int userID = result.getInt("userID");
                    int bookID = result.getInt("bookID");
                    Date rentalDate = result.getDate("rentalDate");
                    Date returnDate = result.getDate("returnDate");
                    Rental rental = new Rental(id, userID, bookID, rentalDate, returnDate);
                    return rental;
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return null;
    }

    @Override
    public Iterable<Rental> findAll() {
        logger.traceEntry("finding all users");
        List<Rental> rentals = new ArrayList<>();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("select * from rentals")){
            try(var result = statement.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    int userID = result.getInt("userID");
                    int bookID = result.getInt("bookID");
                    Date rentalDate = result.getDate("rentalDate");
                    Date returnDate = result.getDate("returnDate");
                    Rental rental = new Rental(id, userID, bookID, rentalDate, returnDate);
                    rentals.add(rental);
                }
            }}
        catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return rentals;
    }

}
