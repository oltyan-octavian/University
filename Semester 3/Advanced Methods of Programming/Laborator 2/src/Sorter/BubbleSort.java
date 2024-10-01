package Sorter;

public class BubbleSort implements AbstractSort {

    @Override
    public void sort(Integer[] array) {
        int i, j, aux;
        for (i = 0; i < array.length; i++) {
            for (j = 0; j < array.length - 1; j++) {
                if (array[j] > array[j + 1]) {
                    aux = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = aux;
                }
            }
        }
    }
}