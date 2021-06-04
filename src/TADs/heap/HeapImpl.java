package TADs.heap;

import java.util.Arrays;

// para el ejercicio 1 implementaria el BST con un array utilizando los mismos metodos de heap solo que sin ordenar
// cuando elimino hago que todos los de abajo suban un lugar en bloque

public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

    private T[] values;
    private int size;
    private final int tipo;

    public HeapImpl(int size, int tipo) { // tipo 1: maximo, tipo 2: minimo
        values = (T[]) new Comparable[size];
        this.tipo = tipo;
    }

    private int getFatherPosition(int position) {
        return (position - 1) / 2;
    }

    private int getLeftChildPosition(int position) {

        if ((2*position) + 1 >= this.size || this.values[(2*position) + 1] == null) {
            return -1;
        }

        else {
            return (2*position) + 1;
        }

    }

    private int getRightChildPosition(int position) {

        if ((2*position) + 2 >= this.size || this.values[(2*position) + 2] == null) {
            return -1;
        }

        else {
            return  (2*position) + 2;
        }

    }

    private void swap(int position1, int position2) {
        T temp = values[position1];
        values[position1] = values[position2];
        values[position2] = temp;
    }

    @Override
    public void insert(T value) {

        if (size == values.length) {
            Comparable[] temp = new Comparable[values.length * 2];  //Comparable<T>[] temp ??

            for (int i = 0; i < values.length; i++) {
                temp[i] = values[i];
            }
            values = (T[]) temp;
        }

        int position = size;
        size++;
        values[position] = value;

        if (tipo == 1) {

            while (position != 0 && values[position].compareTo(values[getFatherPosition(position)]) > 0) {

                values[position] = values[getFatherPosition(position)];
                values[getFatherPosition(position)] = value;

                position = getFatherPosition(position);
            }

        }

        else {

            while (position != 0 && values[position].compareTo(values[getFatherPosition(position)]) < 0) {

                values[position] = values[getFatherPosition(position)];
                values[getFatherPosition(position)] = value;

                position = getFatherPosition(position);
            }

        }

    }

    @Override
    public T delete() throws EmptyHeapException {
        T valueToReturn = values[0];

        if (size == 0) {
            throw new EmptyHeapException();
        }

        else if (size == 1) {
            values[0] = null;
        }

        else if (size == 2) {
            values[0] = values[1];
            values[1] = null;
        }

        else {
            int position = 0;
            values[position] = values[size - 1]; // pongo el ultimo valor en la raiz y lo borro de su lugar original
            values[size - 1] = null;
            int check = 0;

            while (check != -10) {

                if (tipo == 1) {

                    int childMaxPosition = maxPosition(getLeftChildPosition(position), getRightChildPosition(position));

                    if (values[childMaxPosition].compareTo(values[position]) > 0) {

                        swap(childMaxPosition, position);
                        position = childMaxPosition;
                        check = -10;
                    }
                    else {
                        check = -10;
                    }
                }

                else { // tipo == 2

                    int childMinPosition = minPosition(getLeftChildPosition(position), getRightChildPosition(position));

                    if (values[childMinPosition].compareTo(values[position]) < 0) {

                        swap(childMinPosition, position);
                        position = childMinPosition;
                        check = -10;
                    }
                    else {
                        check = -10;
                    }
                }

            }

        }

        size--;
        return valueToReturn;
    }

    private int maxPosition(int position1, int position2) {

        if (position2 == -1) { // O SEA QUE COMO MUCHO SOLO TIENE HIJO IZQUIERDO

            if (position1 == -1) { // O SEA QUE ES HOJA
                return -1;
            }

            else {
                return position1;
            }
        }

        if (values[position1] == null && values[position2] == null) {
            return -1;
        }

        int pos = 0;

        if (position1 <= size && position2 <= size && position1 > 0 && position2 > 0) {
            if (values[position2].compareTo(values[position1]) > 0) {  // values[pos2] > values[pos1]
                pos = position2;
            }
            else {
                pos = position1;
            }
        }

        return pos;
    }

    private int minPosition(int position1, int position2) {

        if (position2 == -1) { // O SEA QUE COMO MUCHO SOLO TIENE HIJO IZQUIERDO

            if (position1 == -1) { // O SEA QUE ES HOJA
                return -1;
            }

            else {
                return position1;
            }
        }

        if (values[position1] == null && values[position2] == null) {
            return -1;
        }

        int pos = 0;

        if (position1 <= size && position2 <= size && position1 > 0 && position2 > 0) {
            if (values[position1].compareTo(values[position2]) > 0) {  // values[pos1] > values[pos2]
                pos = position2;
            }
            else {
                pos = position1;
            }
        }

        return pos;
    }

    @Override
    public T get() throws EmptyHeapException {

        if (size == 0) {
            throw new EmptyHeapException();
        }

        return values[0];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return "HeapImpl{" + "values=" + Arrays.toString(values) + '}';
    }

    public void printHeap() {

        int exp = 0;
        while(Math.pow(2,exp) < size) { // hallo log en base 2 para saber cantidad de niveles
            exp++;
        }

        String str = "";

        int i = 0;
        for (int j = 0; j < exp; j++) {
            while (i < Math.pow(2,j)) {
                str = str + values[i] + " "; // imprimo por nivel
                System.out.println(str);
                i++;
            }
        }

    }

}