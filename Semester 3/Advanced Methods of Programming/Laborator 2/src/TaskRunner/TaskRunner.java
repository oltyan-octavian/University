package TaskRunner;

import Task.Task;

public interface TaskRunner{
    void executeOneTask();
    void executeAll();
    void addTask(Task t);
    boolean hasTask();
}