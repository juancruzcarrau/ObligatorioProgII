package TADs.ListDoble;

public class NodoDoble<T> {

    private NodoDoble<T> previousValue;
    private T value;
    private NodoDoble<T> nextValue;

    public NodoDoble(T value) {
        previousValue = null;
        this.value = value;
        nextValue = null;
    }

    public NodoDoble<T> getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(NodoDoble<T> previousValue) {
        this.previousValue = previousValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public NodoDoble<T> getNextValue() {
        return nextValue;
    }

    public void setNextValue(NodoDoble<T> nextValue) {
        this.nextValue = nextValue;
    }

    @Override
    public String toString() {
        return "Valor del nodo: " + value + ".";
    }

}