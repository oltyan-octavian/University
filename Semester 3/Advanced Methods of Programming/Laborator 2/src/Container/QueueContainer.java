package Container;

import Container.Container;
import Task.Task;

public class QueueContainer extends ClassContainer {
    @Override
    public Task remove() {
        if (!tasks.isEmpty()) {
            return tasks.remove(0);
        } else {
            return null;
        }
    }
}
