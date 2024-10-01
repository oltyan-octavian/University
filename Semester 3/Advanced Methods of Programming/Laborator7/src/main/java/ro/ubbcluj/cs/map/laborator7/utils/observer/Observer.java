package ro.ubbcluj.cs.map.laborator7.utils.observer;


import ro.ubbcluj.cs.map.laborator7.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}