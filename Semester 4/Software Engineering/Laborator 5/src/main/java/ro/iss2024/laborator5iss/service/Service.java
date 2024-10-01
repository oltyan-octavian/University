package ro.iss2024.laborator5iss.service;

import ro.iss2024.laborator5iss.domain.Book;
import ro.iss2024.laborator5iss.domain.Rental;
import ro.iss2024.laborator5iss.domain.User;
import ro.iss2024.laborator5iss.repository.IRepository;
import ro.iss2024.laborator5iss.utils.Observable;
import ro.iss2024.laborator5iss.utils.Observer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Service<ID> implements IService<ID>, Observable {
    private IRepository<Integer, User> userRepository;
    private IRepository<Integer, Book> bookRepository;
    private IRepository<Integer, Rental> rentalRepository;
    private List<Observer> observers=new ArrayList<>();

    public Service(IRepository<Integer, User> userRepository, IRepository<Integer, Book> bookRepository, IRepository<Integer, Rental> rentalRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.rentalRepository = rentalRepository;
    }

    public User connect(String name,String password){
        Iterable<User> allusers=userRepository.findAll();
        for (User us:allusers){
            if (Objects.equals(us.getName(), name) && Objects.equals(us.getPassword(), password))
            {
                return us;
            }
        }
        return null;
    }

    @Override
    public boolean addUser(String name, String password, String PNC, String address, String phoneNumber){
        String type = "client";
        User u = new User(name, password, PNC, address, phoneNumber, type);
        User saved = userRepository.save(u);
        notifyObservers();
        return saved != null;
    }

    public Iterable<Book> getBooksNotRented(){
        Iterable<Book> books = bookRepository.findAll();
        List<Book> notRented = new ArrayList<>();
        for (Book b:books){
            if (!b.isRented()){
                notRented.add(b);
            }
        }
        return notRented;
    }

    public boolean rentBook(int bookID, int userID){
        Book b = bookRepository.findOne(bookID);
        if (b.isRented()){
            return false;
        }
        b.setRented(true);
        bookRepository.update(b);
        Date date = new Date(System.currentTimeMillis());
        Rental r = new Rental(userID, bookID, date, null);
        rentalRepository.save(r);
        notifyObservers();
        return true;
    }

    public List<String> getRentedList(){
        List<String> rentedList = new ArrayList<>();
        Iterable<Rental> rentals = rentalRepository.findAll();
        for (Rental r:rentals){
            if (r.getReturnDate() == null){
                Book b = bookRepository.findOne(r.getBookID());
                User u = userRepository.findOne(r.getUserID());
                rentedList.add(r.getId() + " : " + b.getTitle() + " rented by " + u.getName() + " on " + r.getRentalDate());
            }
        }
        return rentedList;
    }

    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        observers.stream().forEach(x->x.update());
    }

    public void addBook(String title, String author, String publishingHouse, int year) {
        Book b = new Book(title, author, publishingHouse, year, false);
        bookRepository.save(b);
        notifyObservers();
    }

    public void deleteBook(Integer id) {
        bookRepository.delete(id);
        notifyObservers();
    }

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void confirmReturn(int i) {
        Rental r = rentalRepository.findOne(i);
        r.setReturnDate(new Date(System.currentTimeMillis()));
        rentalRepository.update(r);
        Book b = bookRepository.findOne(r.getBookID());
        b.setRented(false);
        bookRepository.update(b);
        notifyObservers();
    }
}
