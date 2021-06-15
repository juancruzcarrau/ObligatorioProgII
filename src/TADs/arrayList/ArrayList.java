package TADs.arrayList;

public interface ArrayList<T extends Comparable<T>> {

    void add(T value);

    T get(int index);

    int size();

    int remove(int index);

    boolean contains(T value);
}
