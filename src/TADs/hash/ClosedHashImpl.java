package TADs.hash;

import java.nio.file.FileSystemAlreadyExistsException;
import java.util.Arrays;

public class ClosedHashImpl<K, V> implements Hash<K, V> {

    private static final int DEFAULT_INITIAL_TABLE_HASH_SIZE = 10;
    private ClosedHashNode[] tableHash;
    private int size = 0;

    public ClosedHashImpl() {
        tableHash = new ClosedHashNode[DEFAULT_INITIAL_TABLE_HASH_SIZE];
    }

    public ClosedHashImpl(int tableHashSize, float loadFactor) {
        tableHash = new ClosedHashNode[tableHashSize];
    }

    @Override
    public String toString() {
        return "ClosedHashImpl{" + "tableHash=" + (Arrays.toString(tableHash)).toString() + "}\n" ;
    }

    @Override
    public void put(K key, V value) {

        for (int i = 0; i < tableHash.length; i++) {
            if (tableHash[i] != null && tableHash[i].getKey().equals(key)) {
                System.out.println("Ya existe un objeto con la key: " + key + ".");
                return;
            }
        }

        if (size >= tableHash.length) {
            System.out.println("No se pudo agregar el elemento asociado a la clave: " + key + ". El hash esta lleno.");
        }

        int positionInHashTable = (key.hashCode() % tableHash.length);
        ClosedHashNode<K,V> newNode = new ClosedHashNode<>(key, value);

        // SI NO HAY COLISION:
        if (tableHash[positionInHashTable] == null || tableHash[positionInHashTable].isDeleted()) {
            tableHash[positionInHashTable] = newNode;
            size++;
        }

        // SI HAY COLISION:
        else {
            int newPosition = positionInHashTable;
            int intento = 1;

            // HAY COLISION y TABLA NO LLENA (busco otra pos.)
            while (tableHash[newPosition] != null && !tableHash[newPosition].isDeleted() && size < tableHash.length) {

                // RESOLUCION LINEAL
                newPosition = ((key.hashCode() + intento) % tableHash.length);

                // RESOLUCION CUADRATICA ??
                //newPosition = (key.hashCode() * key.hashCode()) % tableHash.length; // Math.pow no porque es un double

                intento++;
            }

            if (tableHash[newPosition] == null || tableHash[newPosition].isDeleted()) {
                tableHash[newPosition] = newNode;
                size++;
            }

        }

    }

    @Override
    public V get(K key) {
        int positionInHashTable = (key.hashCode() % tableHash.length);
        if (tableHash[positionInHashTable].getKey().equals(key)) {
            return (V) tableHash[positionInHashTable].getValue();
        }

        else {
            for (int i = 0; i < tableHash.length; i++) {
                if (tableHash[i].getKey().equals(key)) {
                    return (V) tableHash[i].getValue();
                }
            }
        }

        return null;
    }

    @Override
    public void delete(K key) {
        int positionInHashTable = key.hashCode() % tableHash.length;
        if (tableHash[positionInHashTable].getKey().equals(key)) {
            tableHash[positionInHashTable].setDeleted(true);
            size--;
        }

        else { // TENGO QUE RECORRER EL RESTO DE ELEMENTOS PARA VER SI EXISTE UNO CON ESA KEY
            int count = tableHash.length;
            for (int i = 0; i < tableHash.length; i++) {
                if (tableHash[i].getKey().equals(key)) {
                    tableHash[i].setDeleted(true);
                    size--;
                    return;
                }
                count--;
                if (count == 0) {
                    System.out.println("No hay un objeto con la key: " + key + ".");
                }
            }
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(K key) {
        int positionInHashTable = (key.hashCode() % tableHash.length);
        if (tableHash[positionInHashTable].getKey().equals(key)) {
            return true;
        }

        else {
            for (int i = 0; i < tableHash.length; i++) {
                if (tableHash[i].getKey().equals(key)) {
                    return true;
                }
            }
        }

        return false;

    }

}