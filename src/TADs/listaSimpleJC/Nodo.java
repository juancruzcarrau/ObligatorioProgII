package TADs.listaSimpleJC;

public class Nodo {

    private Object object;
    private Nodo nodoSiguiente = null;

    public Nodo(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public Nodo getNodoSiguiente() {
        return nodoSiguiente;
    }

    public void setNodoSiguiente(Nodo nodoSiguiente) {
        this.nodoSiguiente = nodoSiguiente;
    }
}
