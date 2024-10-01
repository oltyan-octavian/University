package Container;

import Container.Container;
import Task.Task;

public class StackContainer extends ClassContainer {
    @Override
    public Task remove() {
        if (!tasks.isEmpty()) {
            return tasks.remove(tasks.size() - 1);
        } else {
            return null;
        }
    }
}
