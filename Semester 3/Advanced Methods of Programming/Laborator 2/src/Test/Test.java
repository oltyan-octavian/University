package Test;

import TaskRunner.*;
import Container.*;
import Sorter.*;
import Task.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Test {
    public Test() {
    }

    public static ArrayList<MessageTask> generateTasks() {
        ArrayList<MessageTask> tasks = new ArrayList<>();
        MessageTask task1 = new MessageTask("ID1", "Descriere1", "Mesaj1", "From1", "To1", LocalDateTime.now());
        MessageTask task2 = new MessageTask("ID2", "Descriere2", "Mesaj2", "From2", "To2", LocalDateTime.now());
        MessageTask task3 = new MessageTask("ID3", "Descriere3", "Mesaj3", "From3", "To3", LocalDateTime.now());
        MessageTask task4 = new MessageTask("ID4", "Descriere4", "Mesaj4", "From4", "To4", LocalDateTime.now());
        MessageTask task5 = new MessageTask("ID5", "Descriere5", "Mesaj5", "From5", "To5", LocalDateTime.now());
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);
        return tasks;
    }

    public static void testTaskRunner(String[] args) {
        ArrayList<MessageTask> tasks = generateTasks();
        System.out.println("Strategy Task:");
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(ContainerStrategy.valueOf(args[0]));
        tasks.forEach(strategyTaskRunner::addTask);
        strategyTaskRunner.executeAll();
        Integer[] array = new Integer[]{1,2,3,7,3};
        SortingTask st = new SortingTask("id1", "desc", array, SortStrategy.BUBBLESORT);
        st.execute();
        System.out.print("\nPrinter Task:\n");
        PrinterTaskRunner printerRunner = new PrinterTaskRunner(strategyTaskRunner);
        tasks.forEach(printerRunner::addTask);
        printerRunner.executeAll();
        QueueContainer queue = new QueueContainer();
        MessageTask task1 = new MessageTask("ID1", "Descriere1", "Mesaj1", "From1", "To1", LocalDateTime.now());
        MessageTask task2 = new MessageTask("ID2", "Descriere2", "Mesaj2", "From2", "To2", LocalDateTime.now());
        MessageTask task3 = new MessageTask("ID3", "Descriere3", "Mesaj3", "From3", "To3", LocalDateTime.now());
        MessageTask task4 = new MessageTask("ID4", "Descriere4", "Mesaj4", "From4", "To4", LocalDateTime.now());
        MessageTask task5 = new MessageTask("ID5", "Descriere5", "Mesaj5", "From5", "To5", LocalDateTime.now());
        queue.add(task1);
        queue.add(task2);
        System.out.println(queue.size());
        System.out.println(queue.remove() == task1);
        System.out.println(queue.size());
        System.out.print("\nDelay Task:\n");
        DelayTaskRunner delayRunner = new DelayTaskRunner(strategyTaskRunner);
        tasks.forEach(delayRunner::addTask);
        delayRunner.executeAll();
    }
}