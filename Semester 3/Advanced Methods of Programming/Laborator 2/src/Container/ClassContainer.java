package Container;

import Task.Task;

import java.util.ArrayList;

public abstract class ClassContainer implements Container {
    ArrayList<Task> tasks = new ArrayList<>();
    public abstract Task remove();
    public void add(Task task){
        tasks.add(task);
    }
    public int size(){
        return tasks.size();
    }
    public boolean isEmpty(){
        return tasks.isEmpty();
    }
}
