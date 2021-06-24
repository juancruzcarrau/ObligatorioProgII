package TADs.hash;

import TADs.arrayList.ArrayListImpl;

import java.util.Arrays;

public class HashCerrado<K, T> implements HashTable<K, T>{

    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private NodoHashCerrado<K, T>[] table;
    private Boolean[] hasBeenDeleted;
    private int size;
    private int amountOfItems = 0;
    private double loadFactor = DEFAULT_LOAD_FACTOR;

    public HashCerrado(int size) {
        this.table = new NodoHashCerrado[size];
        this.hasBeenDeleted = new Boolean[size];
        Arrays.fill(hasBeenDeleted, false);
        this.size = size;
    }
    public HashCerrado(int size, double loadFactor) {
        this.table = new NodoHashCerrado[size];
        this.hasBeenDeleted = new Boolean[size];
        Arrays.fill(hasBeenDeleted, false);
        this.size = size;
        this.loadFactor = loadFactor;
    }

    @Override
    public void put(K key, T value) {

        if(contains(key)){
            throw new RuntimeException();
        }

        //Implementacion cuadratica
        int hash = Math.abs(key.hashCode()) % size;
        int position = hash;
        int colisiones = 1;

        while(table[position] != null){
            position = Math.abs((hash + Math.abs(colisiones*colisiones))) % size;
            colisiones++;
        }

        table[position] = new NodoHashCerrado<>(key, value);
        hasBeenDeleted[position] = false;   //Puede ser un sitio que habia sido previamente elminado
        amountOfItems++;

        if((amountOfItems/(double)size) > loadFactor){
            restructureHash();
        }
    }

    private void restructureHash() {

        int newSize = (int) (size*1.5);
        NodoHashCerrado<K, T>[] newTable = new NodoHashCerrado[newSize];
        Boolean[] newHasBeenDeleted = new Boolean[newSize];
        Arrays.fill(newHasBeenDeleted, false);

        //Implementacion cuadratica
        for (NodoHashCerrado<K, T> nodo : table) {
            if(nodo == null){
                continue;
            }

            int hash = Math.abs(nodo.getKey().hashCode()) % newSize;
            int position = hash;
            int colisiones = 1;

            while(newTable[position] != null){
                position = Math.abs((hash + Math.abs(colisiones*colisiones))) % newSize;
                colisiones++;
            }

            newTable[position] = nodo;
        }

        size = newSize;
        table = newTable;
        hasBeenDeleted = newHasBeenDeleted;
    }

    @Override
    public boolean contains(K key) {

        int hash = Math.abs(key.hashCode()) % size;
        int position = hash;
        int colisiones = 1;

        while(table[position] != null || hasBeenDeleted[position]){
            if (!hasBeenDeleted[position]) {
                if (table[position].getKey().equals(key)) {
                    return true;
                }
            }

            position = Math.abs((hash + Math.abs(colisiones*colisiones))) % size;
            colisiones++;
        }

        return false;
    }

    @Override
    public void remove(K key) {
        if(!contains(key)){
            throw new KeyNoExistenteRuntimeExeption();
        }

        int hash = Math.abs(key.hashCode()) % size;
        int position = hash;
        int colisiones = 1;

        while(true){
            if (!hasBeenDeleted[position]) {
                if (table[position].getKey().equals(key)) {
                    table[position] = null;
                    hasBeenDeleted[position] = true;
                    break;
                }
            }
            position = Math.abs((hash + Math.abs(colisiones*colisiones))) % size;
            colisiones++;
        }
    }

    @Override
    public T get(K key) {
        if(!contains(key)){
            throw new KeyNoExistenteRuntimeExeption();
        }

        int hash = Math.abs(key.hashCode()) % size;
        int position = hash;
        int colisiones = 1;

        while(true){
            if (!hasBeenDeleted[position]) {
                if (table[position].getKey().equals(key)) {
                    return table[position].getData();
                }
            }
            position = Math.abs((hash + Math.abs(colisiones*colisiones))) % size;
            colisiones++;
        }
    }
    
    public ArrayListImpl<K> getKeys(){
        ArrayListImpl<K> keys = new ArrayListImpl<>(amountOfItems);
        for (NodoHashCerrado<K, T> nodo : table) {
            if(nodo != null){
                keys.add(nodo.getKey());
            }
        }

        return keys;
    }

    public ArrayListImpl<T> getValues(){
        ArrayListImpl<T> values = new ArrayListImpl<>(amountOfItems);
        for (NodoHashCerrado<K, T> nodo : table) {
            if(nodo != null){
                values.add(nodo.getData());
            }
        }

        return values;
    }
}
