package TADs.listaSimpleJC;

public class Nodo<T> {

    private final T object;
    private Nodo<T> nodoSiguiente = null;

    public Nodo(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public Nodo<T> getNodoSiguiente() {
        return nodoSiguiente;
    }

    public void setNodoSiguiente(Nodo<T> nodoSiguiente) {
        this.nodoSiguiente = nodoSiguiente;
    }
}
