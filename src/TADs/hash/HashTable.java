package TADs.hash;

public interface HashTable<K, V> {

    void put(K key, V value);

    boolean contains(K key);

    void remove(K clave);

    V get(K key);
}