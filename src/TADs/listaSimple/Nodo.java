package TADs.listaSimple;

public class Nodo<T> {

    private T value;
    private Nodo<T> nextValue;

    public Nodo(T value) {
        this.value = value;
        nextValue = null;
    }

    public T getValue() {
        return value;
    }

    public Nodo<T> getNextValue() {
        return nextValue;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setNextValue(Nodo<T> nextValue) {
        this.nextValue = nextValue;
    }

    @Override
    public String toString() {
        return "Valor del nodo: " + value + ".";
    }

}