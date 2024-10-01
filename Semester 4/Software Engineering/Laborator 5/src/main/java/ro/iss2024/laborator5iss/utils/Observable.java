package ro.iss2024.laborator5iss.utils;



public interface Observable {
    void addObserver(Observer e);
    void removeObserver(Observer e);
    void notifyObservers();
}
