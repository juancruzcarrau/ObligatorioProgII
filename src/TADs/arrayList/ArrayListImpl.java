package TADs.arrayList;

import entities.CauseOfDeath;

import java.util.Arrays;

public class ArrayListImpl<T> implements ArrayList<T>{

    T[] array;
    int last = 0;

    public ArrayListImpl(int initialSize) {
        this.array = (T[]) new Object[initialSize];
    }

    @Override
    public void add(T value) {
        if(last == array.length){
            enlargeArray();
        }
        
        array[last] = value;
        last++;
    }

    private void enlargeArray() {
        T[] newArray = (T[]) new Object[this.array.length*2];    //Array size is duplicated
        System.arraycopy(this.array, 0, newArray, 0, this.array.length);
        this.array = newArray;
    }

    @Override
    public T get(int index) {
        if(index < last){
            return array[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int size() {
        return last;
    }

    @Override
    public int remove(int index) {
        //No hay necesidad de implementarlo en este momento
        return 0;
    }

    @Override
    public boolean contains(T value) {
        for(T element : array){
            if(value.equals(element)){
                return true;
            }
        }

        return false;
    }

    /**
     * Sorts array by merge Sort
     */
    public void sort(){
        //Comparable[] filledArray = (Comparable[]) Arrays.copyOfRange(this.array, 0, last);
        Comparable<T>[] filledArray = new Comparable[last];
        for (int i = 0; i < last; i++) {
            filledArray[i] = (Comparable<T>) array[i];
        }
        mergeSort(filledArray);
        System.arraycopy((T[]) filledArray, 0, this.array, 0, filledArray.length);
    }

    private void mergeSort(Comparable<T>[] array){

        if(array.length <= 1){
            return;
        }

        Comparable<T>[] array1;
        Comparable<T>[] array2;

        if(array.length % 2 == 0){
            array1 = Arrays.copyOfRange(array, 0, array.length/2);
            array2 = Arrays.copyOfRange(array, (array.length/2), array.length);
        } else {
            array1 = Arrays.copyOfRange(array, 0, (array.length + 1)/2);
            array2 = Arrays.copyOfRange(array, ((array.length+ 1) /2), array.length);
        }

        this.mergeSort(array1);
        this.mergeSort(array2);

        int i = 0, j = 0;

        while(i < array1.length && j < array2.length){
            if(array1[i].compareTo((T) array2[j]) < 0){
                array[i+j] = array1[i];
                i++;
            } else {
                array[i+j] = array2[j];
                j++;
            }
        }

        if(i < array1.length){
            //Items left in array1
            while(i < array1.length){
                array[i+j] = array1[i];
                i++;
            }
        } else {
            //Items left in array2
            while(j < array2.length){
                array[i+j] = array2[j];
                j++;
            }
        }

    }
}
