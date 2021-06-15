package TADs.hash;

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

        //Implementacion lineal
        int hash = Math.abs(key.hashCode()) % size;
        int position = hash;
        int collisions = 1;

        try {
            while(table[position] != null){
                position = (hash + collisions) % size;
                collisions++;
            }
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }


        table[position] = new NodoHashCerrado<>(key, value);
        hasBeenDeleted[position] = false;   //Puede ser un sitio que habia sido previamente elminado
        amountOfItems++;

        if((double) (amountOfItems/size) > loadFactor){
            restructureHash();
        }
    }

    private void restructureHash() {
        int newSize = getNextPrime(size);
        NodoHashCerrado<K, T>[] newTable = new NodoHashCerrado[newSize];
        Boolean[] newHasBeenDeleted = new Boolean[newSize];
        Arrays.fill(newHasBeenDeleted, false);

        //Implementacion lineal
        for (NodoHashCerrado<K, T> nodo : table) {
            int hash = Math.abs(nodo.getKey().hashCode()) % newSize;
            int position = hash;
            int collisions = 1;

            while(newTable[position] != null){
                position = (hash + collisions) % newSize;
                collisions++;
            }

            newTable[position] = nodo;
        }

        size = newSize;
        table = newTable;
        hasBeenDeleted = newHasBeenDeleted;
    }

    private int getNextPrime(int size) {
        int number = size + 1;

        while(!isPrime(number)){
            number++;
        }

        return number;
    }

    private boolean isPrime(int number){
        double maxPosibleDivisor = Math.sqrt(number);
        double divisor = 2;
        while(divisor < maxPosibleDivisor){
            if(number % divisor == 0){
                return false;
            }
            divisor++;
        }

        return true;
    }

    @Override
    public boolean contains(K key) {

        int hash = Math.abs(key.hashCode()) % size;
        int position = hash;
        int colisiones = 1;

        Boolean[] hasBeenChecked = new Boolean[size];
        Arrays.fill(hasBeenChecked, false);


        while(table[position] !=null || hasBeenDeleted[position]){
            if (!hasBeenDeleted[position]) {
                if (table[position].getKey().equals(key)) {
                    return true;
                }
            }

            hasBeenChecked[position] = true;
            if(!arrayContains(hasBeenChecked,false)){
                //Todas las posiciones fueron revisadas y no se encontro el elemento
                return false;
            }

            position = (hash + colisiones) % size;
            colisiones++;
        }

        return false;
    }

    private boolean arrayContains(Boolean[] array, boolean value){
        for (boolean b : array){
            if(b == value){
                return true;
            }
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
            position = (hash + colisiones) % size;
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
            position = (hash + colisiones) % size;
            colisiones++;
        }
    }
}
