package TADs.listaSimpleJC;

public interface Lista {

    void add(Object value);

    void remove(int position) throws NodoNoExistenteException;

    Object get(int position) throws NodoNoExistenteException;

}
