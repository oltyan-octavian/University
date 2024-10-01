package TaskRunner;

import Container.*;
import Task.Task;

public class StrategyTaskRunner implements TaskRunner {
    private Container container;
    public StrategyTaskRunner(ContainerStrategy strategy){
        container =  TaskContainerFactory.getInstance().createContainer(strategy);
    }
    public StrategyTaskRunner() {
        super();
    }

    @Override
    public void executeOneTask() {
        Task task = container.remove();
        task.execute();
    }

    @Override
    public void executeAll() {
        while(hasTask())
        {
            executeOneTask();
        }

    }

    @Override
    public void addTask(Task t) {
        container.add(t);
    }

    @Override
    public boolean hasTask() {
        return !container.isEmpty();
    }
}
