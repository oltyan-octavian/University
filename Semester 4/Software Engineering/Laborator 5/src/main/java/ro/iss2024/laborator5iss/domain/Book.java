package ro.iss2024.laborator5iss.domain;

public class Book extends Entity<Integer> {
    private String title;
    private String author;
    private String publishingHouse;
    private int year;
    private boolean rented;

    public Book(Integer integer, String title, String author, String publishingHouse, int year, boolean rented) {
        super(integer);
        this.title = title;
        this.author = author;
        this.publishingHouse = publishingHouse;
        this.year = year;
        this.rented = rented;
    }

    public Book(String title, String author, String publishingHouse, int year, boolean rented) {
        this.title = title;
        this.author = author;
        this.publishingHouse = publishingHouse;
        this.year = year;
        this.rented = rented;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }
}
