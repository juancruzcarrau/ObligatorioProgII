package TADs.listaSimpleJC;


public class ListaEnlazada<T> implements Lista<T> {

    private Nodo<T> nodoRaiz;
    private int size = 0;

    public ListaEnlazada() {
    }

    public int size() {
        return size;
    }

    @Override
    public void add(T value) {
        if (nodoRaiz == null) {
            //En caso de que se esta agregando el primer elemento
            nodoRaiz = new Nodo<>(value);
        } else {
            Nodo<T> ultimoNodo = nodoRaiz;
            while(ultimoNodo.getNodoSiguiente() != null){
                ultimoNodo = ultimoNodo.getNodoSiguiente();
            }
            Nodo<T> nuevoNodo = new Nodo<>(value);
            ultimoNodo.setNodoSiguiente(nuevoNodo);
        }
        size++;
    }

    @Override
    public void remove(int position) throws NodoNoExistenteException {
        if (nodoRaiz != null) {
            int i = 0;
            Nodo<T> nodoActual = nodoRaiz;
            while(nodoActual.getNodoSiguiente() != null && i < position - 1){
                nodoActual = nodoActual.getNodoSiguiente();
                i++;
            }

            if(nodoActual.getNodoSiguiente() == null){
                throw new NodoNoExistenteException("No hay un nodo en la posicion " + (i + 1));
            }

            if (nodoActual.equals(nodoRaiz)) {
                nodoRaiz = nodoRaiz.getNodoSiguiente();
            } else {
                nodoActual.setNodoSiguiente(nodoActual.getNodoSiguiente().getNodoSiguiente());
            }
            size--;
        } else {
            throw new NodoNoExistenteException("No hay ningun nodo en la lista para quitar.");
        }

    }

    @Override
    public T get(int position) throws NodoNoExistenteException {
        int i = 0;
        Nodo<T> nodoActual = nodoRaiz;
        while(nodoActual != null && i < position){
            nodoActual = nodoActual.getNodoSiguiente();
            i++;
        }

        if(nodoActual == null){
            throw new NodoNoExistenteException("No hay un nodo en la posicion " + i);
        }

        return nodoActual.getObject();
    }

    public void addFirst(T object) {
        Nodo<T> nuevoNodo = new Nodo<>(object);
        nodoRaiz.setNodoSiguiente(this.nodoRaiz);
        this.nodoRaiz = nuevoNodo;
    }

    //    void addLast(Object object);       //La funcion add hace lo mismo que add()

    public void print(){
        Nodo<T> nodoActual = nodoRaiz;
        while(nodoActual != null) {
            System.out.println(nodoActual.getObject());
            nodoActual = nodoActual.getNodoSiguiente();
        }
        System.out.println("-------------");

    }

    public boolean contains(Object object){
        Nodo<T> nodoActual = nodoRaiz;
        while(nodoActual != null){
            if(nodoActual.getObject().equals(object)){
                return true;
            }
            nodoActual = nodoActual.getNodoSiguiente();
        }

        return false;
    }

    public ListaEnlazada<T> objetosEnAmbos(ListaEnlazada<T> lista) throws NodoNoExistenteException {
        Nodo<T> nodoUltimo = nodoRaiz;
        ListaEnlazada<T> listaDeRepetidos = new ListaEnlazada<>();

        while(nodoUltimo != null){
            for(int i = 0; i < lista.size(); i++){
                if(nodoUltimo.getObject().equals(lista.get(i))){
                    listaDeRepetidos.add(nodoUltimo.getObject());
                }
            }

            nodoUltimo = nodoUltimo.getNodoSiguiente();
        }

        return listaDeRepetidos;
    }
}
