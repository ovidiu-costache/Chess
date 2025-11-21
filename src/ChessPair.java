package src;

public class ChessPair<K extends Comparable<K>, V> implements Comparable<ChessPair<K, V>> {
    private K key;
    private V value;

    public ChessPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    // Getter and Setter for key and value
    public K getKey() {
        return this.key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return this.value;
    }
    public void setValue(V value) {
        this.value = value;
    }

    @Override
    // "key-value"
    public String toString() {
        return key + "-" + value;
    }

    @Override
    public int compareTo(ChessPair<K, V> obj) {
        return this.key.compareTo(obj.key);
    }
}
