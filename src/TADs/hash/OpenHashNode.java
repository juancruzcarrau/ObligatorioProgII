package TADs.hash;

import java.util.Objects;

public class OpenHashNode<K, V> {

    private K key;
    private V value;

    public OpenHashNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return " {key = " + key + ", value = " + value + "} ";
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}