package ro.iss2024.laborator5iss.domain;

import java.sql.Date;

public class Rental extends Entity<Integer>{
    private int userID;
    private int bookID;
    private Date rentalDate;
    private Date returnDate;

    public Rental(Integer integer, int userID, int bookID, Date rentalDate, Date returnDate) {
        super(integer);
        this.userID = userID;
        this.bookID = bookID;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public Rental(int userID, int bookID, Date rentalDate, Date returnDate) {
        super();
        this.userID = userID;
        this.bookID = bookID;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
