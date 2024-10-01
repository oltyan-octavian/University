package Task;

import java.util.Objects;


public abstract class Task{

    private String id;
    private String description;
    public Task(String i, String description)
    {
        this.id = i;
        this.description =description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
    public abstract void execute();
    @Override
    public String toString() {
        return "Task.Task{" +
                "id='" + id + '\'' +
                ", descriere='" +description + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescriere(String description) {
        this.description = description;
    }
}