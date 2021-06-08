package TADs.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpenHashImpl<K, V> implements Hash<K, V> { // CUANDO DEBERIA AGRANDAR LENGTH DE TABLE.SIZE ??

    private static final int DEFAULT_INITIAL_TABLE_HASH_SIZE = 10;
    private ArrayList<OpenHashNode<K,V>>[] tableHash;
    private int size = 0;

    public OpenHashImpl() {
        tableHash = new ArrayList[DEFAULT_INITIAL_TABLE_HASH_SIZE];
    }

    public OpenHashImpl(int tableHashSize, float loadFactor) {
        tableHash = new ArrayList[tableHashSize];
    }

    @Override
    public String toString() {
        return "ClosedHashImpl{" + "tableHash=" + (Arrays.toString(tableHash)).toString() + '}';
    }

    @Override
    public void put(K key, V value) {
        int positionInHashTable = key.hashCode() % tableHash.length;

        OpenHashNode<K,V> newNode = new OpenHashNode<>(key, value);
        ArrayList<OpenHashNode<K, V>> listForPosition = tableHash[positionInHashTable]; // accedo a la lista en esa pos.

        if (listForPosition == null) {                                             // si es nula, creo una.
            listForPosition = new ArrayList<>();
            tableHash[positionInHashTable] = listForPosition;
        }

        for (int i = 0; i < listForPosition.size(); i++) {
            if (listForPosition != null && listForPosition.get(i) != null && listForPosition.get(i).getKey().equals(key)) {
                System.out.println("Ya existe un objeto con la key: " + key + ".");
                return;
            }
        }

        listForPosition.add(newNode);
        size++;

    }

    @Override
    public V get(K key) {
        int positionInHashTable = key.hashCode() % tableHash.length;
        List<OpenHashNode<K, V>> listForPosition = tableHash[positionInHashTable];

        for (int i = 0; i < listForPosition.size(); i++) {
            if (listForPosition.get(i).getKey().equals(key)) {
                return (V) listForPosition.get(i).getValue();
            }
        }

        return null;
    }

    @Override
    public void delete(K key) {
        int positionInHashTable = key.hashCode() % tableHash.length;
        List<OpenHashNode<K, V>> listForPosition = tableHash[positionInHashTable];
        int count = listForPosition.size();

        for (int i = 0; i < listForPosition.size(); i++) {
            if (listForPosition.get(i).getKey().equals(key)) {
                listForPosition.remove(i);
                size--;
                return;
            }
            count--;
            if (count == 0) {
                System.out.println("No hay un objeto con la key: " + key + ".");
            }
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(K key) {
        int positionInHashTable = key.hashCode() % tableHash.length;
        List<OpenHashNode<K, V>> listForPosition = tableHash[positionInHashTable];

        for (int i = 0; i < listForPosition.size(); i++) {
            if (listForPosition.get(i).getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

}