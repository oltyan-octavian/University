package ro.ubbcluj.cs.map.laborator8.utils.observer;


import ro.ubbcluj.cs.map.laborator8.utils.events.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E t);
}
