package TaskRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrinterTaskRunner extends AbstractTaskRunner{
    public PrinterTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }
    public void executeOneTask() {
        super.executeOneTask();
        System.out.println("Task-ul s-a executat la ora " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH")));
    }

    @Override
    public void executeAll(){
        while(hasTask()){
            executeOneTask();
        }
    }
}
