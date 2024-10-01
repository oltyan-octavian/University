package Container;

public class TaskContainerFactory implements Factory {
    @Override
    public Container createContainer (ContainerStrategy strategy){
        return switch(strategy){
            case LIFO ->new StackContainer();
            case FIFO ->new QueueContainer();
        };
    }

    private static final TaskContainerFactory instance = new TaskContainerFactory();
    private TaskContainerFactory(){}
    public static TaskContainerFactory getInstance(){
        return instance;
    }

}
