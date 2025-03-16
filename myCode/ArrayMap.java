package Map61B;

import org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;


import static org.junit.Assert.*;

public class ArrayMap<K, V> implements Map61B<K, V> {
    private K[] keys;
    private V [] values;
    int size;

    public ArrayMap() {
        keys = (K[]) new Object[100];
        values = (V[]) new Object[100];
        size = 0;
    }

    /**返回键键对应的索引
     * 如果没有就返回-1*/
    private int KeyIndex(K key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public boolean containsKey(K key) {
        int index = KeyIndex(key);
        return index > -1;
    }

    public void put(K key, V value) {
        int index = KeyIndex(key);
        if (index == -1) {
            keys[size] = key;
            values[size] = value;
            size++;
        } else {
            values[index] = value;
        }
    }

    public V get(K key) {
        int index = KeyIndex(key);
        return values[index];
    }

    public int size() {
        return size;
    }

    public List<K> keys() {
        List<K> KeyList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            KeyList.add(keys[i]);
        }
        return KeyList;
    }

}
