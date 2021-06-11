package TADs.hash;

public class NodoHashAbierto<K, T>{

    private K key;
    private T data;

    public NodoHashAbierto(K key, T data) {
        this.key = key;
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
