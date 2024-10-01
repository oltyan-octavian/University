package ro.ubbcluj.cs.map.laborator7.utils.events;


import ro.ubbcluj.cs.map.laborator7.domain.Entity;
import ro.ubbcluj.cs.map.laborator7.domain.Friendship;

public class TaskStatusEvent implements Event {
    private TaskExecutionStatusEventType type;
    private Entity task;
    public TaskStatusEvent(TaskExecutionStatusEventType type, Entity task) {
        this.task=task;
        this.type=type;
    }

    public Entity getTask() {
        return task;
    }

    public void setTask(Entity task) {
        this.task = task;
    }

    public TaskExecutionStatusEventType getType() {
        return type;
    }

    public void setType(TaskExecutionStatusEventType type) {
        this.type = type;
    }
}
