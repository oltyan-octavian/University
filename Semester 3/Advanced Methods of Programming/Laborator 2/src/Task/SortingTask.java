package Task;
import Sorter.*;

import java.util.Arrays;

public class SortingTask extends Task {

    private Integer[] array;
    private AbstractSort sorter;
    public SortingTask(String i, String description, Integer[] array, SortStrategy strategy) {
        super(i, description);
        this.array = array;
        if(strategy == SortStrategy.BUBBLESORT){
            sorter = new BubbleSort();
        }
        if(strategy == SortStrategy.QUICKSORT) {
            sorter = new QuickSort();
        }
    }

    @Override
    public void execute() {
        sorter.sort(array);
        System.out.println("Vectorul este:");
        for (int elem : array) System.out.printf(String.valueOf(elem));
        System.out.print("\n");
    }
}
