package TADs.heap;

public interface Heap<T extends Comparable<T>> {

    void insert(T value);

    T delete() throws EmptyHeapException;

    T get() throws EmptyHeapException;

    int size();

}