package ro.iss2024.laborator5iss.domain;

public class Entity<ID> {
    protected ID id;
    public Entity() {
    }
    public Entity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
