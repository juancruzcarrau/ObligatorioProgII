package TADs.listaSimpleJC;

public interface Lista<T> {

    void add(T value);

    void remove(int position) throws NodoNoExistenteException;

    T get(int position) throws NodoNoExistenteException;

}
