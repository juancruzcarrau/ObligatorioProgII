package TADs.listaSimpleFC;

import TADs.Lista;

public class ListaEnlazada<T> implements Lista<T> {

    private Nodo<T> first;
    private Nodo<T> last;
    private int size = 0;

    public int posicionFromValue (T value) throws NullPointerException {
        int pos = 1;

        if (first.getValue() == value) {
            return 1;
        }

        else if (last.getValue() == value) {
            return size;
        }

        else {
            Nodo<T> temp = first;
            while(temp.getValue() != value) {
                pos++;
                if (pos == size + 1) {
                    throw new NullPointerException();
                }
                temp = temp.getNextValue();
            }
        }

        return pos;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 1; i < size + 1; i++) {
            str = str + "Elemento " + i + ": " + get(i).getValue() + "." + "\n";
        }
        return str;
    }

    @Override
    public void add(T value) {
        Nodo<T> newNodo = new Nodo<>(value);

        if (size == 0) {
            first = newNodo;
        }

        else {
            last.setNextValue(newNodo);
        }

        last = newNodo;
        size++;
    }

    @Override
    public void remove(int position) throws IndexOutOfBoundsException {

        if (size == 0 || position > size || position < 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        else {

            if (position == 1) {
                if (size == 1) {
                    first = null;
                    last = null;
                }
                else {
                    first = first.getNextValue();
                }
            }

            else if (position == size) {
                Nodo<T> test = first;
                int i = 1;
                while (i != position - 1) {
                    test = test.getNextValue();
                    i += 1;
                }
                last = test;
            }

            else {
                Nodo<T> temp = first;
                int i = 1;
                while (i != position - 1) {
                    temp = temp.getNextValue();
                    i += 1;
                }
                temp.setNextValue(temp.getNextValue().getNextValue()); // ENLAZO EL ANTERIOR CON EL SIGUIENTE
            }
        }
        size--;
    }

    @Override
    public Nodo<T> get(int position) throws IndexOutOfBoundsException {
        if (position <= 0 || position > size) {
            throw new IndexOutOfBoundsException();
        }

        else if (position == 1) {
            return first;
        }

        else if (position == size) {
            return last;
        }

        else {

            Nodo<T> temp = first;
            int i = 1;

            while (i != position) {
                temp = temp.getNextValue();
                i++;
            }

            return temp; // Devuelve object si hago return temp.getValue().
        }

    }

    @Override
    public Boolean find(T value) {

        if (first.getValue() == value) {
            return true;
        }

        else {
            Nodo<T> temp = first;
            int i = 1;

            while (temp.getValue() != value) {
                i++;

                if (i == size + 1) {
                    return false;
                }

                temp = temp.getNextValue();
            }

            return true;
        }
    }

    public void addFirst (T value) {
        Nodo<T> newNodo = new Nodo<>(value);

        if (size == 0) {
            last = newNodo;
        }

        else {
            newNodo.setNextValue(first);
            }

        first = newNodo;
        size++;
    }

    public void addLast (T value) {
        Nodo<T> newNodo = new Nodo<>(value);
        add(value);
    }

    public int size() {
        return size;
    }

    public void sort () { // UTILIZO BUBBLE SORT. NECESITO QUE LA LISTA SEA DE INT PARA COMPARAR
        for (int i = 2; i < size + 1; i++) {
            for (int j = 2; j < size-i; j++){

                if ((Integer) first.getValue() > (Integer) get(2).getValue()) {
                    T temp1 = first.getValue();
                    T temp2 = get(2).getValue();

                    first.setValue(temp2);
                    get(2).setValue(temp1);
                }

                else if ((Integer) get(size-1).getValue() > (Integer) last.getValue()){
                    T temp1 = get(size-1).getValue();
                    T temp2 = last.getValue();

                    get(size-1).setValue(temp2);
                    last.setValue(temp1);
                }

                else {
                    Integer elem1 = (Integer) get(j).getValue();
                    Integer elem2 = (Integer) get(j+1).getValue();
                    if (elem1 > elem2) {
                        get(j-1).setNextValue(get(j+1));
                        get(j+1).setNextValue(get(j));
                        get(j).setNextValue(get(j+2));
                    }
                }
            }
        }
    }

    public void addSorted (T value) {
        add(value);
        sort();
    }

    public void visualizar (ListaEnlazada<T> lista) {
        for (int i = 1; i < size + 1; i++) {
            int posicion = (Integer) get(i).getValue();
            System.out.println(lista.get(posicion).toString());
        }
    }

    public void exchange (T value, int dir) throws IndexOutOfBoundsException, NullPointerException { // DIR POR DIRECCION
        int pos = posicionFromValue(value);

        if (dir != -1 && dir != 1) {
            throw new IndexOutOfBoundsException();
        }
        else if (! find(value)) {
            throw new NullPointerException();
        }


        else if (pos == 1 && dir == -1) {
            throw new NullPointerException();
        }

        else if (pos == 1 && dir == 1) {
            T temp1 = first.getValue();
            T temp2 = get(2).getValue();

            first.setValue(temp2);
            get(2).setValue(temp1);
        }

        else if (pos == 2 && dir == -1) {
            T temp1 = first.getValue();
            T temp2 = get(2).getValue();

            first.setValue(temp2);
            get(2).setValue(temp1);
        }

        else if (pos == size && dir == 1) {
            throw new NullPointerException();
        }

        else if (pos == size && dir == -1) {
            T temp1 = get(size-1).getValue();
            T temp2 = last.getValue();

            get(size-1).setValue(temp2);
            last.setValue(temp1);
        }

        else if (pos == size-1 && dir == 1) {
            T temp1 = get(size-1).getValue();
            T temp2 = last.getValue();

            get(size-1).setValue(temp2);
            last.setValue(temp1);
        }

        else {
            if (dir == 1) {
                T temp1 = get(pos).getValue();
                T temp2 = get(pos + 1).getValue();

                get(pos).setValue(temp2);
                get(pos + 1).setValue(temp1);
            }

            if (dir == -1) {
                T temp1 = get(pos - 1).getValue();
                T temp2 = get(pos).getValue();

                get(pos - 1).setValue(temp2);
                get(pos).setValue(temp1);
            }

        }
    }

    public ListaEnlazada<T> both (ListaEnlazada<T> lista) {
        ListaEnlazada<T> total = new ListaEnlazada<T>();

        for (int i = 1; i < size() + 1; i++) {
            for (int j = 1; j < lista.size() + 1; j++){
                if (get(i).getValue() == lista.get(j).getValue()) {
                    total.add(get(i).getValue());
                }
            }
        }
        return total;
    }

    public ListaEnlazada<T> notBoth (ListaEnlazada<T> lista) {
        ListaEnlazada<T> total = new ListaEnlazada<T>();

        for (int i = 1; i < size + 1; i++){ // AGREGO TODOS LOS DE UNA QUE NO ESTEN EN LA OTRA
            int temp = 0;

            for (int j = 1; j < lista.size() + 1; j++) {
                if (get(i).getValue() != lista.get(j).getValue()) {
                    temp++;
                }
            }
            if (temp == size) {
                total.add(get(i).getValue());
            }

        }

        for (int i = 1; i < lista.size + 1; i++){ // AGREGO TODOS LOS DE LA OTRA QUE NO ESTAN EN LA PRIMERA
            int temp = 0;

            for (int j = 1; j < size + 1; j++) {
                if (lista.get(i).getValue() != get(j).getValue()) {
                    temp++;
                }
            }
            if (temp == size) {
                total.add(lista.get(i).getValue());
            }

        }
        return total;
    }

    public ListaEnlazada<T> listaPositivos() {             // EJERCICIO 11)a
        ListaEnlazada<T> positivos = new ListaEnlazada<T>();
        for (int i = 1; i < size() + 1; i++) {
            if ((Integer) get(i).getValue() >= 0) {
                positivos.add(get(i).getValue());
            }
        }
        return positivos;
    }

    public void eliminarNodosPositivos() {              // EJERCICIO 11)b
        while ((Integer) first.getValue() >= 0) {
            remove(1);
        }
        for (int i = 1; i < size() + 1; i++) {
            if ((Integer) get(i).getValue() >= 0) {
                remove(i);
            }
        }
    }

    public static void main(String[] args) {
        ListaEnlazada<Integer> test = new ListaEnlazada<>();
        test.add(24);
        test.add(23);
        test.add(27);

        ListaEnlazada<Integer> test2 = new ListaEnlazada<>();
        test2.add(2);
        test2.add(3);
        test2.add(27);

        System.out.println(test.notBoth(test2).toString());

        //System.out.println(test.toString());
        //test.eliminarNodosPositivos();
        //System.out.println(test.toString());

        //test.exchange(27,1);
        //ListaEnlazada<Integer> test2 = new ListaEnlazada<>();
        //test.add(24);
        //test.add(23);
        //test.add(25);

        //System.out.println(test.both(test2).size);
        //System.out.println(test.both(test2).toString());

        //System.out.println(test.toString());
        //test.sort();
        //System.out.println(test.toString());

        //test.exchange(23,-1);

        //System.out.println(test.find(23));
        //System.out.println(test.first.getValue());
        //test.remove(1);
        //System.out.println(test.first.getValue());
        //System.out.println(test.find(24));
        //System.out.println(test.size());
        //System.out.println(test.find(24));
    }

}