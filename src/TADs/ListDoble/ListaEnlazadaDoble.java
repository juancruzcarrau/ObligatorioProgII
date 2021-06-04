package TADs.ListDoble;

import TADs.Lista;

public class ListaEnlazadaDoble<T> implements Lista<T> {

    private NodoDoble<T> first;
    private NodoDoble<T> last;
    private int size = 0;

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
        NodoDoble<T> newNodo = new NodoDoble<>(value);

        if (size == 0) {
            first = newNodo;
            last = newNodo;
        }

        else {
            last.setNextValue(newNodo);
            newNodo.setNextValue(last);
        }

        last = newNodo;
        size++;
    }

    @Override
    public void remove(int position) {
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
                    first.setPreviousValue(null);
                }
            }

            else if (position == size) {
                NodoDoble<T> test = first;
                int i = 1;
                while (i != position - 1) {
                    test = test.getNextValue();
                    i += 1;
                }
                last = test;
                test.setNextValue(null);
            }

            else {
                NodoDoble<T> temp = first;
                int i = 1;
                while (i != position - 1) {
                    temp = temp.getNextValue();
                    i += 1;
                }
                temp.setNextValue(temp.getNextValue().getNextValue()); // ENLAZO EL ANTERIOR CON EL SIGUIENTE
                temp.getNextValue().getNextValue().setPreviousValue(temp); // ENLAZO EL SIGUIENTE CON EL ANTERIOR
            }
        }
        size--;
    }

    @Override
    public NodoDoble<T> get(int position) throws IndexOutOfBoundsException {
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

            NodoDoble<T> temp = first;
            int i = 1;

            while (i != position) {
                temp = temp.getNextValue();
                i++;
            }

            return temp; // Devuelve object si hago return temp.getValue().
        }

    }

    @Override
    public Boolean find(Object value) {

        if (first.getValue() == value) {
            return true;
        }

        else {
            NodoDoble<T> temp = first;
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
}