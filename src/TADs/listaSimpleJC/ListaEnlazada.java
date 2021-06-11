package TADs.listaSimpleJC;


public class ListaEnlazada implements Lista {

    private Nodo nodoRaiz;
    private int size = 0;

    public ListaEnlazada() {
    }

    public int size() {
        return size;
    }

    @Override
    public void add(Object value) {
        if (nodoRaiz == null) {
            //En caso de que se esta agregando el primer elemento
            nodoRaiz = new Nodo(value);
        } else {
            Nodo ultimoNodo = nodoRaiz;
            while(ultimoNodo.getNodoSiguiente() != null){
                ultimoNodo = ultimoNodo.getNodoSiguiente();
            }
            Nodo nuevoNodo = new Nodo(value);
            ultimoNodo.setNodoSiguiente(nuevoNodo);
        }
        size++;
    }

    @Override
    public void remove(int position) throws NodoNoExistenteException {
        if (nodoRaiz != null) {
            int i = 0;
            Nodo nodoActual = nodoRaiz;
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
    public Object get(int position) throws NodoNoExistenteException {
        int i = 0;
        Nodo nodoActual = nodoRaiz;
        while(nodoActual != null && i < position){
            nodoActual = nodoActual.getNodoSiguiente();
            i++;
        }

        if(nodoActual == null){
            throw new NodoNoExistenteException("No hay un nodo en la posicion " + i);
        }

        return nodoActual.getObject();
    }

    public void addFirst(Object object) {
        Nodo nuevoNodo = new Nodo(object);
        nodoRaiz.setNodoSiguiente(this.nodoRaiz);
        this.nodoRaiz = nuevoNodo;
    }

    //    void addLast(Object object);       //La funcion add hace lo mismo que add()

    public void print(){
        Nodo nodoActual = nodoRaiz;
        while(nodoActual != null) {
            System.out.println(nodoActual.getObject());
            nodoActual = nodoActual.getNodoSiguiente();
        }
        System.out.println("-------------");

    }

    public boolean contains(Object object){
        Nodo nodoActual = nodoRaiz;
        while(nodoActual != null){
            if(nodoActual.getObject().equals(object)){
                return true;
            }
            nodoActual = nodoActual.getNodoSiguiente();
        }

        return false;
    }

    public ListaEnlazada objetosEnAmbos(ListaEnlazada lista) throws NodoNoExistenteException {
        Nodo nodoUltimo = nodoRaiz;
        ListaEnlazada listaDeRepetidos = new ListaEnlazada();

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
